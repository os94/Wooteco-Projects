package com.woowacourse.dsgram.web.controller;

import com.woowacourse.dsgram.service.FollowService;
import com.woowacourse.dsgram.service.dto.follow.FollowRequest;
import com.woowacourse.dsgram.service.dto.user.UserInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class FeedApiController {
    private final FollowService followService;

    public FeedApiController(FollowService followService) {
        this.followService = followService;
    }

    @PostMapping("/follow")
    public ResponseEntity follow(@RequestBody FollowRequest followRequest) {
        followService.follow(followRequest.getFromNickName(), followRequest.getToNickName());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/followers/{nickName}")
    public ResponseEntity<List<UserInfo>> getFollowers(@PathVariable String nickName) {
        List<UserInfo> followers = followService.getFollowers(nickName);
        return ResponseEntity.ok(followers);
    }

    @GetMapping("/followings/{nickName}")
    public ResponseEntity<List<UserInfo>> getFollowings(@PathVariable String nickName) {
        List<UserInfo> followers = followService.getFollowings(nickName);
        return ResponseEntity.ok(followers);
    }
}
