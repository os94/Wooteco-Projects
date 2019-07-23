package techcourse.myblog.domain.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import techcourse.myblog.domain.exception.ArticleNotFoundException;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ArticleServiceTest {
    @Autowired
    private ArticleService articleService;

    @Test
    void article을_찾지못하면_예외처리() {
        assertThrows(ArticleNotFoundException.class, () -> articleService.findById(-1));
    }
}
