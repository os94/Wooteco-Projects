package com.woowacourse.dsgram.web.controller;

import com.woowacourse.dsgram.service.dto.FeedInfo;
import com.woowacourse.dsgram.service.dto.user.LoggedInUser;
import com.woowacourse.dsgram.service.facade.Facade;
import com.woowacourse.dsgram.web.argumentresolver.UserSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class FeedController {
    private final Facade facade;

    public FeedController(Facade facade) {
        this.facade = facade;
    }

    @GetMapping("/user/{nickName}")
    public String showFeed(@PathVariable String nickName, @UserSession LoggedInUser loggedInUser, Model model) {
        FeedInfo feedInfo = facade.getFeedInfo(loggedInUser.getNickName(),nickName);
        model.addAttribute("feedInfo",feedInfo);
        return "my-feed";
    }

}
