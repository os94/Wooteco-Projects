package com.woowacourse.dsgram.domain;

import com.woowacourse.dsgram.domain.exception.InvalidUserException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class UserTest {

    public static final User user = User.builder()
            .email("buddy@buddy.com")
            .userName("김버디")
            .nickName("buddy_")
            .password("Aa12345!")
            .build();

    @Test
    void 회원정보_수정() {
        String sessionEmail = "buddy@buddy.com";
        User updatedUser = User.builder()
                .email("buddy@buddy.com")
                .userName("김디버")
                .nickName("buddy")
                .password("Aa12345!")
                .build();
        user.update(updatedUser, sessionEmail);
        assertThat(user).isEqualTo(updatedUser);
    }

    @Test
    void 회원정보_수정_실패() {
        String sessionEmail = "dubby@dubby.com";
        User updatedUser = User.builder()
                .email("buddy@buddy.com")
                .userName("김디버")
                .nickName("buddy")
                .password("Aa12345!")
                .build();
        assertThatThrownBy(() -> user.update(updatedUser, sessionEmail)).isInstanceOf(InvalidUserException.class);
    }

    @Test
    void OAuth사용자로_변경() {
        user.changeToOAuthUser();
        assertThat(user.isOauthUser()).isEqualTo(true);
    }

    @Test
    void 비밀번호_확인_실패() {
        String password = user.getPassword() + "fail";
        assertThatThrownBy(() -> user.checkPassword(password)).isInstanceOf(InvalidUserException.class);
    }

    @Test
    void 비밀번호_확인() {
        String password = user.getPassword();
        assertThatCode(() -> user.checkPassword(password)).doesNotThrowAnyException();
    }

    @Test
    void 이메일_확인_실패() {
        String email = user.getEmail() + "fail";
        assertThatThrownBy(() -> user.checkEmail(email)).isInstanceOf(InvalidUserException.class);
    }

    @Test
    void 이메일_확인() {
        String email = user.getEmail();
        assertThatCode(() -> user.checkEmail(email)).doesNotThrowAnyException();
    }
}