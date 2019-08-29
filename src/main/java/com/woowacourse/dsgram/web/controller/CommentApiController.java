package com.woowacourse.dsgram.web.controller;

import com.woowacourse.dsgram.domain.Comment;
import com.woowacourse.dsgram.service.CommentService;
import com.woowacourse.dsgram.service.dto.CommentRequest;
import com.woowacourse.dsgram.service.dto.CommentResponse;
import com.woowacourse.dsgram.service.dto.user.LoggedInUser;
import com.woowacourse.dsgram.web.argumentresolver.UserSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentApiController {

    private CommentService commentService;
    private String name;

    public CommentApiController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity create(@RequestBody CommentRequest commentRequest, @UserSession LoggedInUser loggedInUser) {
        Comment savedComment = commentService.create(commentRequest, loggedInUser.getId());
        return new ResponseEntity<>(savedComment, HttpStatus.OK);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity delete(@PathVariable("commentId") Long commentId, @UserSession LoggedInUser loggedInUser) {
        commentService.delete(commentId, loggedInUser.getId());
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity update(@PathVariable("commentId") Long commentId, @UserSession LoggedInUser loggedInUser,
                                 @RequestBody CommentRequest commentRequest) {
        CommentResponse updatedComment = commentService.update(commentId, commentRequest, loggedInUser);
        return new ResponseEntity<>(updatedComment, HttpStatus.OK);
    }

    @GetMapping("/{articleId}")
    public ResponseEntity get(@PathVariable("articleId") Long articleId, @PageableDefault(sort = "id",
            direction = Sort.Direction.DESC, size = 5) Pageable pageable) {
        System.out.println("controller come");
        System.out.println(pageable.getPageSize());
        Page<Comment> comments = commentService.get2(articleId, pageable);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

}