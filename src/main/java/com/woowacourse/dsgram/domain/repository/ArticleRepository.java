package com.woowacourse.dsgram.domain.repository;

import com.woowacourse.dsgram.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findAllByAuthorNickName(String nickName);
}
