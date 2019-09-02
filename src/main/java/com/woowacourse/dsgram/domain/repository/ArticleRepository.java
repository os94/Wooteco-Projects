package com.woowacourse.dsgram.domain.repository;

import com.woowacourse.dsgram.domain.Article;
import com.woowacourse.dsgram.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    Page<Article> findAllByAuthorNickNameOrderByCreatedDateDesc(Pageable pageable, String nickName);

    Page<Article> findByAuthorInOrderByCreatedDateDesc(Pageable pageable, List<User> authors);

    List<Article> findAllByAuthorNickNameOrderByCreatedDateDesc(String nickName);

    List<Article> findAll(Sort sort);
}
