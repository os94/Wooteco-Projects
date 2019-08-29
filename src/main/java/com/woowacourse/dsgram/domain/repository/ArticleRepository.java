package com.woowacourse.dsgram.domain.repository;

import com.woowacourse.dsgram.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findAllByAuthorNickName(String nickName);
}
