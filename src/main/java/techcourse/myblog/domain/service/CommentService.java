package techcourse.myblog.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import techcourse.myblog.domain.Article;
import techcourse.myblog.domain.Comment;
import techcourse.myblog.domain.exception.InvalidAccessException;
import techcourse.myblog.domain.repository.CommentRepository;

import java.util.List;

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
}
