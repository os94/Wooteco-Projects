package model;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserTest {

    @Test
    void User_생성_성공() {
        User user = new User("asdf", "1234", "sean", "vsh12@naver.com");
        Map<String, String> userInfo = new HashMap<>();
        userInfo.put("userId", "asdf");
        userInfo.put("password", "1234");
        userInfo.put("name", "sean");
        userInfo.put("email", "vsh12@naver.com");

        assertThat(User.createUser(userInfo)).isEqualTo(user);
    }

    @Test
    void User_생성_실패() {
        User user = new User("asdf", "1234", "sean", "vsh12@naver.com");
        Map<String, String> userInfo = new HashMap<>();
        userInfo.put("userId", "asdf");
        userInfo.put("password", "1234");
        userInfo.put("name", "sean");

        assertThrows(UserCreateException.class, () -> {
            User.createUser(userInfo);
        });
    }
}