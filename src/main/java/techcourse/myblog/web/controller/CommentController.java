package techcourse.myblog.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import techcourse.myblog.domain.model.Article;
import techcourse.myblog.domain.model.Comment;
import techcourse.myblog.domain.model.User;
import techcourse.myblog.domain.service.ArticleService;
import techcourse.myblog.domain.service.CommentService;
import techcourse.myblog.dto.CommentRequestDto;

import javax.servlet.http.HttpSession;

import static techcourse.myblog.web.SessionManager.USER;

@Controller
@RequestMapping("/articles/{articleId}/comments")
public class CommentController {
    private final CommentService commentService;
    private final ArticleService articleService;

    @Autowired
    public CommentController(CommentService commentService, ArticleService articleService) {
        this.commentService = commentService;
        this.articleService = articleService;
    }

    @PostMapping("")
    public RedirectView createComment(CommentRequestDto commentRequestDto, HttpSession httpSession) {
        Comment comment = convert(commentRequestDto, httpSession);
        commentService.save(comment);
        return new RedirectView("/articles/" + commentRequestDto.getArticleId());
    }

    @PutMapping("/{commentId}")
    public RedirectView updateComment(@PathVariable long articleId, @PathVariable long commentId, String contents, HttpSession httpSession) {
        User loginUser = (User) httpSession.getAttribute(USER);
        commentService.updateByIdAsAuthor(commentId, contents, loginUser);
        return new RedirectView("/articles/" + articleId);
    }

    @DeleteMapping("/{commentId}")
    public RedirectView deleteComment(@PathVariable long commentId, long articleId, HttpSession httpSession) {
        User loginUser = (User) httpSession.getAttribute(USER);
        commentService.deleteByIdAsAuthor(commentId, loginUser);
        return new RedirectView("/articles/" + articleId);
    }

    private Comment convert(CommentRequestDto commentRequestDto, HttpSession httpSession) {
        User author = (User) httpSession.getAttribute(USER);
        Article article = articleService.findById(commentRequestDto.getArticleId());
        return new Comment(commentRequestDto.getContents(), author, article);
    }
}
