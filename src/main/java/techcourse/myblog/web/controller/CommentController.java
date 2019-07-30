package techcourse.myblog.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import techcourse.myblog.domain.Article;
import techcourse.myblog.domain.Comment;
import techcourse.myblog.domain.User;
import techcourse.myblog.domain.service.ArticleService;
import techcourse.myblog.domain.service.CommentService;
import techcourse.myblog.dto.CommentRequestDto;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

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
        User loginUser = (User) httpSession.getAttribute("user");
        User commentAuthor = commentService.findAuthorById(commentId);
        commentService.validate(commentAuthor.getId(), loginUser.getId());
        commentService.update(commentId, contents);
        return new RedirectView("/articles/" + articleId);
    }

    @DeleteMapping("/{commentId}")
    public RedirectView deleteComment(@PathVariable long commentId, long articleId, HttpSession httpSession) {
        User loginUser = (User) httpSession.getAttribute("user");
        User commentAuthor = commentService.findAuthorById(commentId);
        commentService.validate(commentAuthor.getId(), loginUser.getId());
        commentService.deleteById(commentId);
        return new RedirectView("/articles/" + articleId);
    }

    private Comment convert(CommentRequestDto commentRequestDto, HttpSession httpSession) {
        User author = (User) httpSession.getAttribute("user");
        Article article = articleService.findById(commentRequestDto.getArticleId());
        return new Comment(commentRequestDto.getContents(), LocalDateTime.now(), author, article);
    }
}
