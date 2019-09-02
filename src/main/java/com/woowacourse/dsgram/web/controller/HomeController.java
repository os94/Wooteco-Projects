package com.woowacourse.dsgram.web.controller;

import com.woowacourse.dsgram.domain.User;
import com.woowacourse.dsgram.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class HomeController {
    private final UserService userService;

    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String showMainPage(Model model) {
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
