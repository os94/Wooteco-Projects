package com.woowacourse.dsgram.service.dto;

import com.woowacourse.dsgram.domain.User;
import com.woowacourse.dsgram.service.dto.follow.FollowRelation;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Setter
@ToString
public class FeedInfo {
    private User user;
    private long followers;
    private long followings;
    private FollowRelation followRelation;

    @Builder
    public FeedInfo(User user, long followers, long followings, FollowRelation followRelation) {
        this.user = user;
        this.followers = followers;
        this.followings = followings;
        this.followRelation = followRelation;
    }
}
