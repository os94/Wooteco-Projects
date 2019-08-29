package com.woowacourse.dsgram.service;

import com.woowacourse.dsgram.domain.Article;
import com.woowacourse.dsgram.domain.Comment;
import com.woowacourse.dsgram.domain.repository.ArticleRepository;
import com.woowacourse.dsgram.domain.repository.CommentRepository;
import com.woowacourse.dsgram.domain.repository.UserRepository;
import com.woowacourse.dsgram.service.dto.CommentRequest;
import com.woowacourse.dsgram.service.dto.CommentResponse;
import com.woowacourse.dsgram.service.dto.user.LoggedInUser;
import com.woowacourse.dsgram.service.exception.EmptyCommentRequestException;
import com.woowacourse.dsgram.service.exception.NotFoundArticleException;
import com.woowacourse.dsgram.service.exception.NotFoundCommentException;
import com.woowacourse.dsgram.service.exception.NotFoundUserException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommentService {
    private static final int BLANK_CONTENTS = 0;
    private CommentRepository commentRepository;
    private ArticleRepository articleRepository;
    private UserRepository userRepository;

    public CommentService(CommentRepository commentRepository, ArticleRepository articleRepository,
                          UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
    }

    public Comment create(CommentRequest commentRequest, Long userId) {
        if (isBlank(commentRequest.getContents())) {
            throw new EmptyCommentRequestException("댓글의 내용을 입력해주세요.");
        }
        Comment comment = toComment(commentRequest, userId);

        return commentRepository.save(comment);
    }

    // todo : apache common의 StringsUtils.isBlank() 사용하는 것으로 수정.
    private boolean isBlank(String contents) {
        return contents.replaceAll(" ", "").length() == BLANK_CONTENTS;
    }

    private Comment toComment(CommentRequest commentRequest, Long userId) {
        return Comment.builder()
                .contents(commentRequest.getContents())
                .article(articleRepository.findById(commentRequest.getArticleId()).orElseThrow(NotFoundArticleException::new))
                .user(userRepository.findById(userId).orElseThrow(NotFoundUserException::new))
                .build();
    }

    public void delete(Long commentId, long userId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(NotFoundCommentException::new);
        comment.checkAccessibleAuthor(userId);
        commentRepository.delete(comment);
    }

    @Transactional
    public CommentResponse update(Long commentId, CommentRequest commentRequest, LoggedInUser loggedInUser) {
        if (isBlank(commentRequest.getContents())) {
            throw new EmptyCommentRequestException("댓글의 내용을 입력해주세요.");
        }
        Comment comment = commentRepository.findById(commentId).orElseThrow(NotFoundCommentException::new);
        comment.checkAccessibleAuthor(loggedInUser.getId());
        comment.update(commentRequest);
        return new CommentResponse(commentRequest.getArticleId(), commentId, commentRequest.getContents());
    }

    public Page<Comment> get(Long articleId, Pageable pageable) {
        return commentRepository.findByArticle_Id(articleId, pageable);
    }
}