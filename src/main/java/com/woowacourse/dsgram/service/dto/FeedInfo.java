package com.woowacourse.dsgram.service.dto;

import com.woowacourse.dsgram.domain.Article;
import com.woowacourse.dsgram.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FeedInfo {
    private User user;
    private long followers;
    private long followings;
    private List<Article> articles;
    private FollowRelation followRelation;

    @Builder
    public FeedInfo(User user, long followers, long followings, List<Article> articles, FollowRelation followRelation) {
        this.user = user;
        this.followers = followers;
        this.followings = followings;
        this.articles = articles;
        this.followRelation = followRelation;
    }
}
