package techcourse.myblog.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import techcourse.myblog.domain.model.Article;
import techcourse.myblog.domain.model.Comment;
import techcourse.myblog.domain.model.User;
import techcourse.myblog.domain.service.ArticleService;
import techcourse.myblog.domain.service.CommentService;
import techcourse.myblog.dto.CommentRequest;
import techcourse.myblog.dto.CommentResponse;
import techcourse.myblog.dto.CommentsResponse;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

import static techcourse.myblog.web.SessionManager.SESSION_USER;

@Controller
@RequestMapping("/articles/{articleId}/comments")
public class CommentController {
    private static final Logger log = LoggerFactory.getLogger(CommentController.class);
    private final CommentService commentService;
    private final ArticleService articleService;

    @Autowired
    public CommentController(CommentService commentService, ArticleService articleService) {
        this.commentService = commentService;
        this.articleService = articleService;
    }

    @GetMapping
    public ResponseEntity<CommentsResponse> selectComments(@PathVariable long articleId) {
        Article article = articleService.findById(articleId);
        List<Comment> comments = commentService.findByArticle(article);
        CommentsResponse commentsResponse = convert(comments);
        return new ResponseEntity<>(commentsResponse, HttpStatus.OK);
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createComment(@PathVariable long articleId, @RequestBody CommentRequest commentRequest, HttpSession httpSession) {
        Comment comment = convert(articleId, commentRequest, httpSession);
        Comment savedComment = commentService.save(comment);
        log.info("Create Comment: {}", savedComment);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity updateComment(@PathVariable long commentId, @RequestBody String contents, HttpSession httpSession) {
        User loginUser = (User) httpSession.getAttribute(SESSION_USER);
        commentService.updateByIdAsAuthor(commentId, contents, loginUser);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity deleteComment(@PathVariable long commentId, HttpSession httpSession) {
        User loginUser = (User) httpSession.getAttribute(SESSION_USER);
        commentService.deleteByIdAsAuthor(commentId, loginUser);
        return new ResponseEntity(HttpStatus.OK);
    }

    private Comment convert(long articleId, CommentRequest commentRequest, HttpSession httpSession) {
        User author = (User) httpSession.getAttribute(SESSION_USER);
        Article article = articleService.findById(articleId);
        return new Comment(commentRequest.getContents(), author, article);
    }

    private CommentsResponse convert(List<Comment> comments) {
        return new CommentsResponse(
                comments.stream()
                        .map(comment -> new CommentResponse(comment.getId(), comment.getContents(), comment.getLastModifiedDate(), comment.getAuthor().getName()))
                        .collect(Collectors.toList()));
    }
}
