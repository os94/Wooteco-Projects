package model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserTest {

    @Test
    void User_생성_성공() {
        User user = new User("sean", "1234", "sos", "sean@naver.com");
        assertNotNull(user);
    }

    @Test
    void User_생성_실패() {
        assertThrows(UserCreateException.class, () -> {
            new User(null, "1234", "sos", "sean@naver.com");
        });
    }

    @Test
    void match_userId() {
        User user = new User("sean", "1234", "sos", "sean@naver.com");
        assertThat(user.matchId("sean")).isTrue();
    }

    @Test
    void match_userId_and_password() {
        User user = new User("sean", "1234", "sos", "sean@naver.com");
        assertThat(user.matchIdAndPassword("sean", "1234")).isTrue();
    }
}