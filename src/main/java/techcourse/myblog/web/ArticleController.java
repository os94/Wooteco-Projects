package techcourse.myblog.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import techcourse.myblog.domain.Article;
import techcourse.myblog.domain.service.ArticleService;

import javax.transaction.Transactional;

@Controller
@RequestMapping("/articles")
public class ArticleController {
    private static final String ARTICLE = "article";

    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @Transactional
    @PostMapping("")
    public String createArticle(Article newArticle) {
        newArticle = articleService.save(newArticle);
        return "redirect:/articles/" + newArticle.getId();
    }

    @GetMapping("/{id}")
    public String selectArticle(@PathVariable long id, Model model) {
        model.addAttribute(ARTICLE, articleService.findById(id));
        return "article";
    }

    @GetMapping("/{id}/edit")
    public String moveArticleEditPage(@PathVariable long id, Model model) {
        model.addAttribute(ARTICLE, articleService.findById(id));
        return "article-edit";
    }

    @Transactional
    @PutMapping("/{id}")
    public String updateArticle(Article updatedArticle, Model model) {
        updatedArticle = articleService.save(updatedArticle);
        model.addAttribute(ARTICLE, updatedArticle);
        return "redirect:/articles/" + updatedArticle.getId();
    }

    @Transactional
    @DeleteMapping("/{id}")
    public String deleteArticle(@PathVariable long id) {
        articleService.deleteById(id);
        return "redirect:/";
    }
}
