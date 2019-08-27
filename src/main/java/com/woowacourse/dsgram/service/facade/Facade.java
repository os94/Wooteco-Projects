package com.woowacourse.dsgram.service.facade;

import com.woowacourse.dsgram.domain.Article;
import com.woowacourse.dsgram.domain.User;
import com.woowacourse.dsgram.service.ArticleService;
import com.woowacourse.dsgram.service.FollowService;
import com.woowacourse.dsgram.service.UserService;
import com.woowacourse.dsgram.service.dto.FeedInfo;
import com.woowacourse.dsgram.service.dto.FollowInfo;
import com.woowacourse.dsgram.service.dto.FollowRelation;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Facade {
    private final UserService userService;
    private final FollowService followService;
    private final ArticleService articleService;

    public Facade(UserService userService, FollowService followService, ArticleService articleService) {
        this.userService = userService;
        this.followService = followService;
        this.articleService = articleService;
    }

    public FeedInfo getFeedInfo(String fromNickName, String toNickName) {
        User guest = userService.findByNickName(fromNickName);
        User feedOwner = userService.findByNickName(toNickName);
        long followers =  followService.getCountOfFollowers(feedOwner);
        long followings =  followService.getCountOfFollowings(feedOwner);
        List<Article> articles = articleService.findArticlesByAuthorNickName(toNickName);
        FollowRelation followRelation = followService.isFollowed(guest,feedOwner);

        return FeedInfo.builder()
                .user(feedOwner)
                .followers(followers)
                .followings(followings)
                .articles(articles)
                .followRelation(followRelation)
                .build();
    }

    public void follow(String fromNickName, String toNickName) {
        User guest = userService.findByNickName(fromNickName);
        User feedOwner = userService.findByNickName(toNickName);
        followService.save(guest,feedOwner);
    }


    public void unfollow(String fromNickName, String toNickName) {
        User guest = userService.findByNickName(fromNickName);
        User feedOwner = userService.findByNickName(toNickName);
        followService.delete(guest,feedOwner);
    }

    public List<FollowInfo>
    getFollowers(String nickName) {
        User user = userService.findByNickName(nickName);

        return followService.findFollowers(user);
    }

    public List<FollowInfo> getFollowings(String nickName) {
        User user = userService.findByNickName(nickName);

        return followService.findFollowers(user);
    }
}
