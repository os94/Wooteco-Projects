package com.woowacourse.dsgram.web.controller;

import com.woowacourse.dsgram.domain.Article;
import com.woowacourse.dsgram.service.ArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/articles")
public class ArticleController {

    private ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/writing")
    public String moveToWritePage() {
        return "article-edit";
    }

    @GetMapping("/{articleId}")
    public String showArticle(@PathVariable long articleId, Model model) {
        Article article = articleService.findById(articleId);
        model.addAttribute("article", article);
        return "article";
    }
}
