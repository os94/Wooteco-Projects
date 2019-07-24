package techcourse.myblog.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import techcourse.myblog.domain.Article;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    Article findArticleById(long id);
}
