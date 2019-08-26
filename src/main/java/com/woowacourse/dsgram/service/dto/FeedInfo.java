package com.woowacourse.dsgram.service.dto;

import com.woowacourse.dsgram.domain.Article;
import com.woowacourse.dsgram.domain.Follow;
import com.woowacourse.dsgram.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FeedInfo {
    User user;
    List<Follow> followers;
    List<Follow> followings;
    List<Article> articles;

    @Builder
    public FeedInfo(User user, List<Follow> followers, List<Follow> followings, List<Article> articles) {
        this.user = user;
        this.followers = followers;
        this.followings = followings;
        this.articles = articles;
    }
}
