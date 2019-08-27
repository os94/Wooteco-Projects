package com.woowacourse.dsgram.service.dto;

import com.woowacourse.dsgram.domain.Follow;
import com.woowacourse.dsgram.domain.User;

public enum FollowRelation {
    FOLLOW,UNFOLLOW,MINE;

    public static FollowRelation getRelation(Follow follow, User guest, User feedOwner) {
        if(guest.equals(feedOwner)) {
            return MINE;
        }
        if(follow == null) {
            return UNFOLLOW;
        }
        return FOLLOW;
    }
}
