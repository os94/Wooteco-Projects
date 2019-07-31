package techcourse.myblog.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import techcourse.myblog.domain.exception.CommentNotFoundException;
import techcourse.myblog.domain.exception.MisMatchAuthorException;
import techcourse.myblog.domain.model.Article;
import techcourse.myblog.domain.model.Comment;
import techcourse.myblog.domain.model.User;
import techcourse.myblog.domain.repository.CommentRepository;

import java.util.List;

import static java.util.Collections.unmodifiableList;

@Service
public class CommentService {
    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Transactional(readOnly = true)
    public List<Comment> findByArticle(Article article) {
        return unmodifiableList(commentRepository.findCommentsByArticle(article));
    }

    @Transactional(readOnly = true)
    public Comment findById(long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new CommentNotFoundException("댓글을 찾지 못했습니다."));
    }

    @Transactional
    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    @Transactional
    public Comment updateByIdAsAuthor(long id, String contents, User user) {
        Comment comment = findById(id);
        if (!comment.isAuthor(user)) {
            throw new MisMatchAuthorException("작성자만 접근가능합니다.");
        }
        return comment.update(contents);
    }

    @Transactional
    public void deleteByIdAsAuthor(long id, User user) {
        if (!findById(id).isAuthor(user)) {
            throw new MisMatchAuthorException("작성자만 접근가능합니다.");
        }
        commentRepository.deleteById(id);
    }
}
