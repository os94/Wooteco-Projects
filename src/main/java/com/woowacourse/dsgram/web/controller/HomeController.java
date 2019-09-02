package com.woowacourse.dsgram.web.controller;

import com.woowacourse.dsgram.domain.User;
import com.woowacourse.dsgram.service.ArticleService;
import com.woowacourse.dsgram.service.UserService;
import com.woowacourse.dsgram.service.dto.user.LoggedInUser;
import com.woowacourse.dsgram.web.argumentresolver.UserSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class HomeController {
    private final UserService userService;
    private final ArticleService articleService;

    public HomeController(UserService userService, ArticleService articleService) {
        this.userService = userService;
        this.articleService = articleService;
    }

    @GetMapping("/")
    public String showMainPage(@UserSession LoggedInUser loggedInUser, Model model) {
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "index";
    }

    @GetMapping("/tags/{query}")
    public String showSearchPage(@PathVariable String query, Model model) {
        model.addAttribute("query", query);
        return "search-result";
    }
}
