package techcourse.myblog.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ArticleRepositoryTest {
    @Autowired
    private ArticleRepository articleRepository;

    @Test
    void id로_Article_조회() {
        Article article = new Article();
        article.setTitle("title");
        article.setCoverUrl("coverUrl");
        article.setContents("contents");

        articleRepository.save(article);
        assertThat(articleRepository.findArticleById(1)).isEqualTo(article);
    }
}
