package com.woowacourse.dsgram.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/articles")
public class ArticleController {
    private ArticleController() {
    }

    @GetMapping("/writing")
    public String moveToWritePage() {
        return "article-edit";
    }

    @GetMapping("/{articleId}")
    public String showArticle(@PathVariable long articleId, Model model) {
        model.addAttribute("articleId", articleId);
        return "article";
    }
}
