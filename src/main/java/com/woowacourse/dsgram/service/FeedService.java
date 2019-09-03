package com.woowacourse.dsgram.service;

import com.woowacourse.dsgram.domain.User;
import com.woowacourse.dsgram.service.dto.FeedInfo;
import com.woowacourse.dsgram.service.dto.follow.FollowRelation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FeedService {

    private final UserService userService;
    private final FollowService followService;

    public FeedService(UserService userService, FollowService followService) {
        this.userService = userService;
        this.followService = followService;
    }

    @Transactional(readOnly = true)
    public FeedInfo getFeedInfo(String fromNickName, String toNickName) {
        User guest = userService.findByNickName(fromNickName);
        User feedOwner = userService.findByNickName(toNickName);
        long followers = followService.getCountOfFollowers(feedOwner);
        long followings = followService.getCountOfFollowings(feedOwner);
        FollowRelation followRelation = followService.isFollowed(guest, feedOwner);

        return FeedInfo.builder()
                .user(feedOwner)
                .followers(followers)
                .followings(followings)
                .followRelation(followRelation)
                .build();
    }
}
