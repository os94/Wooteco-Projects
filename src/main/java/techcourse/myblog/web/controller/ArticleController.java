package techcourse.myblog.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import techcourse.myblog.domain.model.Article;
import techcourse.myblog.domain.model.User;
import techcourse.myblog.domain.service.ArticleService;
import techcourse.myblog.domain.service.CommentService;
import techcourse.myblog.dto.ArticleDto;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static techcourse.myblog.web.SessionManager.SESSION_USER;

@Controller
@RequestMapping("/articles")
public class ArticleController {
    private static final Logger log = LoggerFactory.getLogger(ArticleController.class);
    private static final String ARTICLE = "article";
    private static final String COMMENTS = "comments";

    private final ArticleService articleService;
    private final CommentService commentService;

    @Autowired
    public ArticleController(ArticleService articleService, CommentService commentService) {
        this.articleService = articleService;
        this.commentService = commentService;
    }

    @PostMapping("")
    public RedirectView createArticle(@Valid ArticleDto newArticleDto, HttpSession httpSession) {
        User loginUser = (User) httpSession.getAttribute(SESSION_USER);
        Article article = articleService.save(newArticleDto.toEntity(loginUser));
        log.info("Create Article: {}", article);
        return new RedirectView("/articles/" + article.getId());
    }

    @GetMapping("/{articleId}")
    public String selectArticle(@PathVariable long articleId, Model model) {
        Article article = articleService.findById(articleId);
        model.addAttribute(ARTICLE, article);
        model.addAttribute(COMMENTS, commentService.findByArticle(article));
        return "article";
    }

    @GetMapping("/{articleId}/edit")
    public String moveArticleEditPage(@PathVariable long articleId, Model model, HttpSession httpSession) {
        User loginUser = (User) httpSession.getAttribute(SESSION_USER);
        Article article = articleService.findByIdAsAuthor(articleId, loginUser);
        model.addAttribute(ARTICLE, article);
        return "article-edit";
    }

    @PutMapping("/{articleId}")
    public RedirectView updateArticle(@PathVariable long articleId, @Valid ArticleDto updateArticleDto, Model model, HttpSession httpSession) {
        User loginUser = (User) httpSession.getAttribute(SESSION_USER);
        Article updatedArticle = articleService.updateByIdAsAuthor(articleId, updateArticleDto.toEntity(loginUser));
        model.addAttribute(ARTICLE, updatedArticle);
        return new RedirectView("/articles/" + updatedArticle.getId());
    }

    @DeleteMapping("/{articleId}")
    public RedirectView deleteArticle(@PathVariable long articleId, HttpSession httpSession) {
        User loginUser = (User) httpSession.getAttribute(SESSION_USER);
        articleService.deleteByIdAsAuthor(articleId, loginUser);
        return new RedirectView("/");
    }
}
