package com.woowacourse.dsgram.domain.repository;

import com.woowacourse.dsgram.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}
