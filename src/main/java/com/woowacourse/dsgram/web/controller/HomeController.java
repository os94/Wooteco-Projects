package com.woowacourse.dsgram.web.controller;

import com.woowacourse.dsgram.domain.Article;
import com.woowacourse.dsgram.service.dto.user.LoggedInUser;
import com.woowacourse.dsgram.service.facade.Facade;
import com.woowacourse.dsgram.web.argumentresolver.UserSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class HomeController {
    private final Facade facade;

    public HomeController(Facade facade) {
        this.facade = facade;
    }

    @GetMapping("/")
    public String showMainPage(@UserSession LoggedInUser loggedInUser, Model model) {
        List<Article> articles = facade.getArticlesByFollowings(loggedInUser.getNickName());
        model.addAttribute("articles", articles);
        return "index";
    }

    @GetMapping("/tags/{query}")
    public String showSearchPage(@PathVariable String query, Model model) {
        model.addAttribute("query", query);
        return "search-result";
    }
}
