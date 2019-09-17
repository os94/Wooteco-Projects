package model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {

    @Test
    void createUser() {
        String parameter = "userId=asdf&password=1234&name=서오상&email=vsh12@naver.com";
        User user = new User("asdf", "1234", "서오상", "vsh12@naver.com");

        assertThat(User.createUser(parameter)).isEqualTo(user);
    }
}