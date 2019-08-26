package com.woowacourse.dsgram.web.controller;

import com.woowacourse.dsgram.service.FeedService;
import com.woowacourse.dsgram.service.dto.FeedInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class FeedController {
    private final FeedService feedService;

    public FeedController(FeedService feedService) {
        this.feedService = feedService;
    }

    // TODO: 2019-08-20 my feed뿐만 아니라, 다른 user feed도 사용할 수 있도록 이름 바꾸기
    // TODO: 2019-08-20 real instagram은 뒤에 email을 붙임.... 우린 users/1/edit, myfeed/1 이렇게 될거같은데 바꿔야될듯ㅎㅎ^^;;;;;;;;
    // TODO: 2019-08-25 real instagram 은 자기 닉네임을 뒤에 붙임
    @GetMapping("/user/{nickName}")
    public String showFeed(@PathVariable String nickName, Model model) {
        FeedInfo feedInfo = feedService.getFeedInfo(nickName);
        model.addAttribute("feedInfo",feedInfo);
        return "my-feed";
    }

}
