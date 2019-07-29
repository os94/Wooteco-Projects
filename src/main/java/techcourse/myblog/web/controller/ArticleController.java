package techcourse.myblog.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import techcourse.myblog.domain.Article;
import techcourse.myblog.domain.User;
import techcourse.myblog.domain.service.ArticleService;
import techcourse.myblog.dto.ArticleRequestDto;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/articles")
public class ArticleController {
    private static final String ARTICLE = "article";

    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping("")
    public String createArticle(@Valid ArticleRequestDto newArticleDto, HttpSession httpSession) {
        User author = (User) httpSession.getAttribute("user");
        Article article = articleService.save(newArticleDto.toEntity(author));
        return "redirect:/articles/" + article.getId();
    }

    @GetMapping("/{articleId}")
    public String selectArticle(@PathVariable long articleId, Model model) {
        model.addAttribute(ARTICLE, articleService.findById(articleId));
        return "article";
    }

    @GetMapping("/{articleId}/edit")
    public String moveArticleEditPage(@PathVariable long articleId, Model model, HttpSession httpSession) {
        User author = (User) httpSession.getAttribute("user");
        Article article = articleService.findById(articleId);
        articleService.validate(article.getAuthor().getId(), author.getId());
        model.addAttribute(ARTICLE, article);
        return "article-edit";
    }

    @PutMapping("/{articleId}")
    public String updateArticle(@PathVariable long articleId, @Valid ArticleRequestDto updateArticleDto, Model model, HttpSession httpSession) {
        User author = (User) httpSession.getAttribute("user");
        Article updateArticle = articleService.update(articleId, updateArticleDto.toEntity(author));
        model.addAttribute(ARTICLE, updateArticle);
        return "redirect:/articles/" + updateArticle.getId();
    }

    @DeleteMapping("/{articleId}")
    public String deleteArticle(@PathVariable long articleId) {
        articleService.deleteById(articleId);
        return "redirect:/";
    }
}
