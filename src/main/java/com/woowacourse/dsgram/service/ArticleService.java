package com.woowacourse.dsgram.service;

import com.woowacourse.dsgram.domain.Article;
import com.woowacourse.dsgram.domain.FileInfo;
import com.woowacourse.dsgram.domain.LikeRelation;
import com.woowacourse.dsgram.domain.User;
import com.woowacourse.dsgram.domain.repository.ArticleRepository;
import com.woowacourse.dsgram.domain.repository.CommentRepository;
import com.woowacourse.dsgram.domain.repository.LikeRelationRepository;
import com.woowacourse.dsgram.service.assembler.ArticleAssembler;
import com.woowacourse.dsgram.service.assembler.UserAssembler;
import com.woowacourse.dsgram.service.dto.FeedInfo;
import com.woowacourse.dsgram.service.dto.article.ArticleEditRequest;
import com.woowacourse.dsgram.service.dto.article.ArticleInfo;
import com.woowacourse.dsgram.service.dto.article.ArticleRequest;
import com.woowacourse.dsgram.service.dto.follow.FollowRelation;
import com.woowacourse.dsgram.service.dto.user.LoggedInUser;
import com.woowacourse.dsgram.service.dto.user.UserInfo;
import com.woowacourse.dsgram.service.strategy.ArticleFileNamingStrategy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;
    private final LikeRelationRepository likeRelationRepository;
    private final HashTagService hashTagService;
    private final FileService fileService;
    private final UserService userService;
    private final FollowService followService;

    public ArticleService(ArticleRepository articleRepository, CommentRepository commentRepository, LikeRelationRepository likeRelationRepository, HashTagService hashTagService
            , FileService fileService, UserService userService, FollowService followService) {
        this.articleRepository = articleRepository;
        this.commentRepository = commentRepository;
        this.likeRelationRepository = likeRelationRepository;
        this.hashTagService = hashTagService;
        this.fileService = fileService;
        this.userService = userService;
        this.followService = followService;
    }

    @Transactional
    public long createAndFindId(ArticleRequest articleRequest, LoggedInUser loggedInUser) {
        return create(articleRequest, loggedInUser).getId();
    }

    private Article create(ArticleRequest articleRequest, LoggedInUser loggedInUser) {
        FileInfo fileInfo = fileService.save(articleRequest.getFile(), new ArticleFileNamingStrategy());

        Article article = Article.builder()
                .contents(articleRequest.getContents())
                .fileInfo(fileInfo)
                .author(userService.findUserById(loggedInUser.getId()))
                .build();

        hashTagService.saveHashTags(article);

        return articleRepository.save(article);
    }

    @Transactional(readOnly = true)
    public Article findById(long articleId) {
        return articleRepository
                .findById(articleId)
                .orElseThrow(() -> new EntityNotFoundException(articleId + "번 게시글을 조회하지 못했습니다."));
    }

    @Transactional(readOnly = true)
    public List<Article> findAll() {
        return articleRepository.findAll(new Sort(Sort.Direction.DESC, "id"));
    }

    @Transactional
    public void update(long articleId, ArticleEditRequest articleEditRequest, LoggedInUser loggedInUser) {
        Article article = findById(articleId);
        Article updatedArticle = article.update(articleEditRequest.getContents(), loggedInUser.getId());

        hashTagService.update(updatedArticle);
    }

    @Transactional
    public void delete(long articleId, LoggedInUser loggedInUser) {
        Article article = findById(articleId);
        article.checkAccessibleAuthor(loggedInUser.getId());
        articleRepository.delete(article);
    }

    public byte[] findFileById(long articleId) {
        return fileService.readFileByFileInfo(findById(articleId).getFileInfo());
    }

    public List<Article> findArticlesByAuthorNickName(String nickName) {
        userService.findByNickName(nickName);
        return articleRepository.findAllByAuthorNickNameOrderByCreatedDateDesc(nickName);
    }

    public Page<ArticleInfo> findArticlesByAuthorNickName(int page, String nickName, long viewerId) {
        userService.findByNickName(nickName);
        return articleRepository
                .findAllByAuthorNickNameOrderByCreatedDateDesc(PageRequest.of(page, 10), nickName)
                .map(article -> findArticleInfo(article.getId(), viewerId));
    }

    public ArticleInfo findArticleInfo(long articleId, long viewerId) {
        long countOfComments = getCountOfComments(articleId);
        long countOfLikes = getCountOfLikes(articleId);

        return ArticleAssembler.toArticleInfo(findById(articleId), countOfComments,
                countOfLikes, isLike(articleId, viewerId));
    }

    private long getCountOfLikes(long articleId) {
        return likeRelationRepository.countByArticleId(articleId);
    }

    private boolean isLike(long articleId, long viewerId) {
        return likeRelationRepository.existsByArticleIdAndUserId(articleId, viewerId);
    }

    private long getCountOfComments(long articleId) {
        return commentRepository.countByArticleId(articleId);
    }

    public Page<ArticleInfo> getArticlesByFollowings(long viewerId, int page) {
        User user = userService.findUserById(viewerId);
        List<User> followings = followService.findFollowings(user)
                .stream().map(followInfo -> userService.findByNickName(followInfo.getNickName()))
                .collect(toList());
        Page<Article> articles = articleRepository.findByAuthorInOrderByCreatedDateDesc(PageRequest.of(page, 10), followings);

        return articles.map(
                article -> findArticleInfo(article.getId(), viewerId)
        );
    }

    public FeedInfo getFeedInfo(String fromNickName, String toNickName) {
        User guest = userService.findByNickName(fromNickName);
        User feedOwner = userService.findByNickName(toNickName);
        long followers = followService.getCountOfFollowers(feedOwner);
        long followings = followService.getCountOfFollowings(feedOwner);
        List<Article> articles = findArticlesByAuthorNickName(toNickName);
        FollowRelation followRelation = followService.isFollowed(guest, feedOwner);

        return FeedInfo.builder()
                .user(feedOwner)
                .followers(followers)
                .followings(followings)
                .articles(articles)
                .followRelation(followRelation)
                .build();
    }

    @Transactional
    public long like(long articleId, long userId) {
        if (likeRelationRepository.existsByArticleIdAndUserId(articleId, userId)) {
            likeRelationRepository.deleteByArticleIdAndUserId(articleId, userId);
            return likeRelationRepository.countByArticleId(articleId);
        }

        LikeRelation likeRelation = new LikeRelation(findById(articleId), userService.findUserById(userId));
        likeRelationRepository.save(likeRelation);
        return likeRelationRepository.countByArticleId(articleId);
    }

    @Transactional(readOnly = true)
    public List<UserInfo> findLikerListById(long articleId) {
        return likeRelationRepository.findAllByArticleId(articleId)
                .stream().map(LikeRelation::getUser)
                .map(UserAssembler::toFollowInfo)
                .collect(toList());
    }
}
