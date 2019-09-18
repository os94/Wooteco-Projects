package model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {

    @Test
    void createUser() {
        String parameter = "userId=asdf&password=1234&name=sean&email=vsh12@naver.com";
        User user = new User("asdf", "1234", "sean", "vsh12@naver.com");

        assertThat(User.createUser(parameter)).isEqualTo(user);
    }
}