package com.woowacourse.dsgram.service;

import com.woowacourse.dsgram.domain.Article;
import com.woowacourse.dsgram.domain.Comment;
import com.woowacourse.dsgram.domain.repository.CommentRepository;
import com.woowacourse.dsgram.service.dto.CommentRequest;
import com.woowacourse.dsgram.service.dto.CommentResponse;
import com.woowacourse.dsgram.service.dto.user.LoggedInUser;
import com.woowacourse.dsgram.service.exception.EmptyCommentRequestException;
import com.woowacourse.dsgram.service.exception.NotFoundCommentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CommentService {
    private static final int BLANK_CONTENTS = 0;

    private CommentRepository commentRepository;
    private UserService userService;
    private ArticleService articleService;

    public CommentService(CommentRepository commentRepository, UserService userService, ArticleService articleService) {
        this.commentRepository = commentRepository;
        this.userService = userService;
        this.articleService = articleService;
    }

    public Comment create(CommentRequest commentRequest, Long userId) {
        if (isBlank(commentRequest.getContents())) {
            throw new EmptyCommentRequestException("댓글의 내용을 입력해주세요.");
        }
        Comment comment = toComment(commentRequest, userId);

        return commentRepository.save(comment);
    }

    private boolean isBlank(String contents) {
        return contents.replaceAll(" ", "").length() == BLANK_CONTENTS;
    }

    private Comment toComment(CommentRequest commentRequest, Long userId) {
        return Comment.builder()
                .contents(commentRequest.getContents())
                .article(articleService.findById(commentRequest.getArticleId()))
                .user(userService.findUserById(userId))
                .build();

    }

    public void delete(Long commentId, long userId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(NotFoundCommentException::new);
        comment.checkAccessibleAuthor(userId);
        commentRepository.delete(comment);
    }

    public CommentResponse update(Long commentId, CommentRequest commentRequest, LoggedInUser loggedInUser) {
        if (isBlank(commentRequest.getContents())) {
            throw new EmptyCommentRequestException("댓글의 내용을 입력해주세요.");
        }
        Comment comment = commentRepository.findById(commentId).orElseThrow(NotFoundCommentException::new);
        comment.checkAccessibleAuthor(loggedInUser.getId());
        comment.update(commentRequest);
        return new CommentResponse(commentRequest.getArticleId(), commentId, commentRequest.getContents());
    }

    @Transactional(readOnly = true)
    public Page<Comment> get(Long articleId, Pageable pageable) {
        return commentRepository.findByArticleId(articleId, pageable);
    }

    public long countByArticleId(long articleId) {
        return commentRepository.countByArticleId(articleId);
    }
}