package com.woowacourse.dsgram.service;

import com.woowacourse.dsgram.domain.Article;
import com.woowacourse.dsgram.domain.Follow;
import com.woowacourse.dsgram.domain.User;
import com.woowacourse.dsgram.domain.repository.FollowRepository;
import com.woowacourse.dsgram.service.dto.FeedInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedService {
    private final UserService userService;
    private final ArticleService articleService;
    private final FollowRepository followRepository;

    public FeedService(UserService userService, ArticleService articleService, FollowRepository followRepository) {
        this.userService = userService;
        this.articleService = articleService;
        this.followRepository = followRepository;
    }

    public FeedInfo getFeedInfo(String nickName) {
        User user = userService.findByNickName(nickName);
        List<Follow> followers =  followRepository.findAllByTo(user);
        List<Follow> followings =  followRepository.findAllByFrom(user);
        List<Article> articles = articleService.findArticlesByAuthorNickName(nickName);

        return FeedInfo.builder()
                .user(user)
                .followers(followers)
                .followings(followings)
                .articles(articles)
                .build();
    }
}
