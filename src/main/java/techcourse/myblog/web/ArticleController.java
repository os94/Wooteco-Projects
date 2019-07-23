package techcourse.myblog.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import techcourse.myblog.domain.Article;
import techcourse.myblog.domain.service.ArticleService;
import techcourse.myblog.dto.ArticleRequestDto;

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
    public String createArticle(ArticleRequestDto newArticleDto) {
        Article article = articleService.save(newArticleDto.toEntity());
        return "redirect:/articles/" + article.getId();
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
    public String updateArticle(@PathVariable long id, ArticleRequestDto updateArticleDto, Model model) {
        Article updateArticle = articleService.update(id, updateArticleDto.toEntity());
        model.addAttribute(ARTICLE, updateArticle);
        return "redirect:/articles/" + updateArticle.getId();
    }

    @Transactional
    @DeleteMapping("/{id}")
    public String deleteArticle(@PathVariable long id) {
        articleService.deleteById(id);
        return "redirect:/";
    }
}
