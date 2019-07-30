package techcourse.myblog.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import techcourse.myblog.domain.Article;
import techcourse.myblog.domain.Comment;
import techcourse.myblog.domain.User;
import techcourse.myblog.domain.exception.CommentNotFoundException;
import techcourse.myblog.domain.exception.InvalidAccessException;
import techcourse.myblog.domain.repository.CommentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Transactional
    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    @Transactional(readOnly = true)
    public List<Comment> findByArticle(Article article) {
        return commentRepository.findCommentsByArticle(article);
    }

    @Transactional
    public void deleteById(long id) {
        commentRepository.deleteById(id);
    }

    @Transactional
    public Comment update(long commentId, String contents) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new InvalidAccessException("error"));
        comment.setContents(contents);
        return comment;
    }

    public User findAuthorById(long id) {
        Optional<Comment> comment = commentRepository.findById(id);
        if (comment.isPresent()) {
            return comment.get().getAuthor();
        }
        throw new CommentNotFoundException("댓글을 찾지 못했습니다.");
    }

    public void validate(long authorId, long loginUserId) {
        if (authorId == loginUserId) {
            return;
        }
        throw new InvalidAccessException("잘못된 접근입니다.");
    }
}
