package com.woowacourse.dsgram.domain.repository.query;

public class FollowQuery {
    public static final String FIND_FOLLOWING_USER_BY_QUERY =
            "SELECT follow.to " +
                    "FROM " +
                    "Follow follow " +
                    "WHERE follow.from = :followedUser";
}
