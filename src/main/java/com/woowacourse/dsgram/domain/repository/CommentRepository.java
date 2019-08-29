package com.woowacourse.dsgram.domain.repository;

import com.woowacourse.dsgram.domain.Article;
import com.woowacourse.dsgram.domain.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByArticle(Article article);

    long countByArticleId(long articleId);

    Page<Comment> findByArticle_Id(Long articleId, Pageable pageable);
}