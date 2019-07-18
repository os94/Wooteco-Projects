package techcourse.myblog.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import techcourse.myblog.domain.Article;
import techcourse.myblog.domain.ArticleNotFoundException;
import techcourse.myblog.domain.ArticleRepository;

import javax.transaction.Transactional;

@Controller
@RequestMapping("/articles")
public class ArticleController {
    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Transactional
    @PostMapping("")
    public String createArticle(Article newArticle) {
        newArticle = articleRepository.save(newArticle);
        return "redirect:/articles/" + newArticle.getId();
    }

    @GetMapping("/{id}")
    public String selectArticle(@PathVariable long id, Model model) {
        model.addAttribute(
                "article",
                articleRepository.findById(id).orElseThrow(ArticleNotFoundException::new));
        return "article";
    }

    @GetMapping("/{id}/edit")
    public String updateArticleForm(@PathVariable long id, Model model) {
        model.addAttribute(
                "article",
                articleRepository.findById(id).orElseThrow(ArticleNotFoundException::new));
        return "article-edit";
    }

    @Transactional
    @PutMapping("/{id}")
    public String updateArticle(Article updatedArticle, Model model) {
        updatedArticle = articleRepository.save(updatedArticle);
        model.addAttribute("article", updatedArticle);
        return "redirect:/articles/" + updatedArticle.getId();
    }

    @Transactional
    @DeleteMapping("/{id}")
    public String deleteArticle(@PathVariable long id) {
        articleRepository.deleteById(id);
        return "redirect:/";
    }
}
