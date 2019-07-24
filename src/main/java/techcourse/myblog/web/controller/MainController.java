package techcourse.myblog.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import techcourse.myblog.domain.service.ArticleService;

@Controller
public class MainController {
    private final ArticleService articleService;

    @Autowired
    public MainController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/")
    public String moveMainPage(Model model) {
        model.addAttribute("articles", articleService.findAll());
        return "index";
    }

    @GetMapping("/writing")
    public String moveArticleWritePage() {
        return "article-edit";
    }
}
