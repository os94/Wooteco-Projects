package com.woowacourse.dsgram.domain.repository;

import com.woowacourse.dsgram.domain.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    long countByArticleId(long articleId);

    Page<Comment> findByArticleId(Long articleId, Pageable pageable);
}