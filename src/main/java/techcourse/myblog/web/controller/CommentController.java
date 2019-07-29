package techcourse.myblog.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;
import techcourse.myblog.domain.Article;
import techcourse.myblog.domain.Comment;
import techcourse.myblog.domain.User;
import techcourse.myblog.domain.service.ArticleService;
import techcourse.myblog.domain.service.CommentService;
import techcourse.myblog.dto.CommentRequestDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

@Controller
public class CommentController {
    private final CommentService commentService;
    private final ArticleService articleService;

    @Autowired
    public CommentController(CommentService commentService, ArticleService articleService) {
        this.commentService = commentService;
        this.articleService = articleService;
    }

    @PostMapping("/comments")
    public RedirectView createComment(CommentRequestDto commentRequestDto, HttpServletRequest request, HttpSession httpSession) {
        Comment comment = convert(commentRequestDto, httpSession);
        commentService.save(comment);
        return new RedirectView("/articles/" + commentRequestDto.getArticleId());
    }

    private Comment convert(CommentRequestDto commentRequestDto, HttpSession httpSession) {
        User author = (User) httpSession.getAttribute("user");
        Article article = articleService.findById(commentRequestDto.getArticleId());
        return new Comment(commentRequestDto.getContents(), LocalDateTime.now(), author, article);
    }
}
