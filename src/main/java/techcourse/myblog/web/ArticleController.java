package techcourse.myblog.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import techcourse.myblog.domain.Article;
import techcourse.myblog.domain.ArticleRepository;

@Controller
public class ArticleController {
    @Autowired
    private ArticleRepository articleRepository;

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
    public String createArticle(Article article, Model model) {
        model.addAttribute("article", article);
        this.articleRepository.add(article);
        return "article";
    }

    @GetMapping("/articles/{articleId}")
    public String selectArticle(@PathVariable int articleId, Model model) {
        model.addAttribute("article", this.articleRepository.findById(articleId));
        return "article";
    }
}
