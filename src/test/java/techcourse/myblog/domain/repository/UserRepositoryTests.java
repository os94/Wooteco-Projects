package techcourse.myblog.domain.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import techcourse.myblog.domain.Article;
import techcourse.myblog.domain.User;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class UserRepositoryTests {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void findById() {
        User user = new User("sean", "sean@gmail.com", "Woowahan123!");
        User persistUser = testEntityManager.persist(user);

        Article article = new Article("title", "coverUrl", "contents");
        article.setAuthor(persistUser);
        testEntityManager.persist(article);

        testEntityManager.flush();
        testEntityManager.clear();

        User actualUser = userRepository.findById(persistUser.getId()).get();

        assertThat(actualUser.getArticles().size()).isEqualTo(1);
    }

    @Test
    public void findById2() {
        // 주인 Entity가 아닌 종속부에서 값을 설정하면 적용되지 않는다
        Article article = new Article("title", "coverUrl", "contents");
        Article persistArticle = testEntityManager.persist(article);

        User user = new User("sean", "sean@gmail.com", "Woowahan123!");
        user.addArticle(persistArticle);
        User persistUser = testEntityManager.persist(user);

        testEntityManager.flush();
        testEntityManager.clear();

        User actualUser = userRepository.findById(persistUser.getId()).get();

        assertThat(actualUser.getArticles().size()).isEqualTo(0);
    }
}
