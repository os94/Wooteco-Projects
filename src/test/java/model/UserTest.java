package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserTest {

    @Test
    void User_생성_성공() {
        User user = new User("1", "1234", "sean", "sean@naver.com");
        assertNotNull(user);
    }

    @Test
    void User_생성_실패() {
        assertThrows(UserCreateException.class, () -> {
            new User(null, "1234", "sean", "sean@naver.com");
        });
    }
}