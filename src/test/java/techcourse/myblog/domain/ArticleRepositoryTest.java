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
    void findAll_호출후_값_조작시_에러_테스트() {
        assertThrows(UnsupportedOperationException.class, () -> articleRepository.findAll().add(new Article()));
    }

    @Test
    void 아이디값으로_조회_테스트() {
        assertThat(testArticle).isEqualTo(articleRepository.findById(1));
    }

    @Test
    void 다음_아이디값_생성_테스트() {
        assertThat(articleRepository.newArticleId()).isEqualTo(2);
    }

    @Test
    void article_업데이트_테스트() {
        Article articleToCompare = new Article();
        articleToCompare.setId(1);
        articleRepository.update(1, articleToCompare);
        assertThat(articleRepository.findById(1)).isEqualTo(articleToCompare);
    }

    @Test
    void article_삭제_테스트() {
        articleRepository.remove(1);
        assertThrows(IllegalArgumentException.class, () -> articleRepository.findById(1));
    }
}
