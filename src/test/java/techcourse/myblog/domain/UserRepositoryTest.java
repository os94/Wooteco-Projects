package techcourse.myblog.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import techcourse.myblog.domain.repository.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User("sean", "sean@gmail.com", "Woowahan123!");
        userRepository.save(user);
    }

    @Test
    void id로_User_조회() {
        assertThat(userRepository.findUserById(1)).isEqualTo(user);
    }

    @Test
    void email로_User_조회() {
        assertThat(userRepository.findUserByEmail("sean@gmail.com")).isEqualTo(user);
    }
}
