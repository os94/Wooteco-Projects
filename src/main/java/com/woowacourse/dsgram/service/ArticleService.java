package com.woowacourse.dsgram.service;

import com.woowacourse.dsgram.domain.Article;
import com.woowacourse.dsgram.domain.FileInfo;
import com.woowacourse.dsgram.domain.LikeRelation;
import com.woowacourse.dsgram.domain.User;
import com.woowacourse.dsgram.domain.repository.ArticleRepository;
import com.woowacourse.dsgram.domain.repository.LikeRelationRepository;
import com.woowacourse.dsgram.service.assembler.ArticleAssembler;
import com.woowacourse.dsgram.service.assembler.UserAssembler;
import com.woowacourse.dsgram.service.dto.LikeResponse;
import com.woowacourse.dsgram.service.dto.article.ArticleEditRequest;
import com.woowacourse.dsgram.service.dto.article.ArticleInfo;
import com.woowacourse.dsgram.service.dto.article.ArticleRequest;
import com.woowacourse.dsgram.service.dto.user.LoggedInUser;
import com.woowacourse.dsgram.service.dto.user.UserInfo;
import com.woowacourse.dsgram.service.strategy.ArticleFileNamingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@Transactional
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final LikeRelationRepository likeRelationRepository;

    @Autowired
    private CommentService commentService;

    private final HashTagService hashTagService;
    private final FileService fileService;
    private final UserService userService;
    private final FollowService followService;

    public ArticleService(ArticleRepository articleRepository, LikeRelationRepository likeRelationRepository, HashTagService hashTagService
            , FileService fileService, UserService userService, FollowService followService) {
        this.articleRepository = articleRepository;
        this.likeRelationRepository = likeRelationRepository;
        this.hashTagService = hashTagService;
        this.fileService = fileService;
        this.userService = userService;
        this.followService = followService;
    }

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

    public void update(long articleId, ArticleEditRequest articleEditRequest, LoggedInUser loggedInUser) {
        Article article = findById(articleId);
        Article updatedArticle = article.update(articleEditRequest.getContents(), loggedInUser.getId());

        hashTagService.update(updatedArticle);
    }

    public void delete(long articleId, LoggedInUser loggedInUser) {
        Article article = findById(articleId);
        article.checkAccessibleAuthor(loggedInUser.getId());
        articleRepository.delete(article);
    }

    @Transactional(readOnly = true)
    public byte[] findFileById(long articleId) {
        return fileService.readFileByFileInfo(findById(articleId).getFileInfo());
    }

    @Transactional(readOnly = true)
    public Page<ArticleInfo> findArticlesByAuthorNickName(int page, String nickName) {
        userService.findByNickName(nickName);
        return articleRepository
                .findAllByAuthorNickNameOrderByCreatedDateDesc(PageRequest.of(page, 10), nickName)
                .map(article -> findArticleInfo(article.getId()));
    }

    @Transactional(readOnly = true)
    public ArticleInfo findArticleInfo(long articleId) {
        return ArticleAssembler.toArticleInfo(findById(articleId));
    }

    private long getCountOfLikes(long articleId) {
        return likeRelationRepository.countByArticleId(articleId);
    }

    private boolean isLike(long articleId, long viewerId) {
        return likeRelationRepository.existsByArticleIdAndUserId(articleId, viewerId);
    }

    private long getCountOfComments(long articleId) {
        return commentService.countByArticleId(articleId);
    }

    @Transactional(readOnly = true)
    public Page<ArticleInfo> getArticlesByFollowings(long viewerId, int page) {
        User user = userService.findUserById(viewerId);
        List<User> followings = followService.findFollowings(user)
                .stream().map(followInfo -> userService.findByNickName(followInfo.getNickName()))
                .collect(toList());
        Page<Article> articles = articleRepository.findByAuthorInOrderByCreatedDateDesc(PageRequest.of(page, 10), followings);

        return articles.map(
                article -> findArticleInfo(article.getId())
        );
    }

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

    public LikeResponse findLikeStatus(long articleId, long viewerId) {
        long countOfLikes = getCountOfLikes(articleId);
        boolean likeStatus = isLike(articleId, viewerId);

        return new LikeResponse(countOfLikes, likeStatus);
    }
}
