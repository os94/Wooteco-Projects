package techcourse.myblog.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ArticleRepositoryTest {
    private ArticleRepository articleRepository;
    private Article testArticle;

    @BeforeEach
    void setUp() {
        articleRepository = new ArticleRepository();
        testArticle = new Article();
        testArticle.setId(1);
        testArticle.setTitle("2");
        testArticle.setCoverUrl("3");
        testArticle.setContents("4");
        articleRepository.add(testArticle);
    }

    @Test
    void findAll_호출후_값_조작시_에러() {
        assertThrows(UnsupportedOperationException.class, () -> articleRepository.findAll().add(new Article()));
    }

    @Test
    void id값으로_조회() {
        assertThat(testArticle).isEqualTo(articleRepository.findById(1));
    }

    @Test
    void 존재하지않는_id값으로_조회시_에러() {
        assertThrows(ArticleNotFoundException.class, () -> articleRepository.findById(2));
    }

    @Test
    void 다음_id값_생성() {
        assertThat(articleRepository.newArticleId()).isEqualTo(2);
    }

    @Test
    void article_업데이트() {
        Article articleToCompare = new Article();
        articleToCompare.setId(1);
        articleRepository.update(articleToCompare);
        assertThat(articleRepository.findById(1)).isEqualTo(articleToCompare);
    }

    @Test
    void article_삭제() {
        articleRepository.remove(1);
        assertThrows(ArticleNotFoundException.class, () -> articleRepository.findById(1));
    }
}
