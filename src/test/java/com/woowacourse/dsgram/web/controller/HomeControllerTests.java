package com.woowacourse.dsgram.web.controller;

import com.woowacourse.dsgram.service.dto.user.AuthUserRequest;
import com.woowacourse.dsgram.service.dto.user.SignUpUserRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HomeControllerTests extends AbstractControllerTest {

    private String cookie;

    @BeforeEach
    void setUp() {
        SignUpUserRequest signUpUserRequest = new SignUpUserRequest(AUTO_INCREMENT + "test", "test", "1234", AUTO_INCREMENT + "test2@test.com");
        defaultSignUp(signUpUserRequest, true);
        cookie = getCookie(new AuthUserRequest(signUpUserRequest.getEmail(), signUpUserRequest.getPassword()));
    }

    @Test
    void 로그인했을_때_index페이지_이동_성공() {
        webTestClient.get()
                .uri("/")
                .header("cookie", cookie)
                .exchange()
                .expectStatus()
                .isOk();
    }

}
