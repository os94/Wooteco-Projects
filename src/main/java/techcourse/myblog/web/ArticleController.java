package techcourse.myblog.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import techcourse.myblog.domain.*;

@Controller
public class ArticleController {
    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("articles", articleRepository.findAll());
        return "index";
    }

    @GetMapping("/writing")
    public String createArticleForm() {
        return "article-edit";
    }

    @PostMapping("/articles")
    public String createArticle(Article newArticle) {
        newArticle = articleRepository.save(newArticle);
        return "redirect:/articles/" + newArticle.getId();
    }

    @GetMapping("/articles/{id}")
    public String selectArticle(@PathVariable long id, Model model) {
        model.addAttribute(
                "article",
                articleRepository.findById(id).orElseThrow(ArticleNotFoundException::new));
        return "article";
    }

    @GetMapping("/articles/{id}/edit")
    public String updateArticleForm(@PathVariable long id, Model model) {
        model.addAttribute(
                "article",
                articleRepository.findById(id).orElseThrow(ArticleNotFoundException::new));
        return "article-edit";
    }

    @PutMapping("/articles/{id}")
    public String updateArticle(Article updatedArticle, Model model) {
        updatedArticle = articleRepository.save(updatedArticle);
        model.addAttribute("article", updatedArticle);
        return "redirect:/articles/" + updatedArticle.getId();
    }

    @DeleteMapping("/articles/{id}")
    public String deleteArticle(@PathVariable long id) {
        articleRepository.deleteById(id);
        return "redirect:/";
    }
}
