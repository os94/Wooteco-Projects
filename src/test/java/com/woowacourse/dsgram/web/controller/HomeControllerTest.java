package com.woowacourse.dsgram.web.controller;

import com.woowacourse.dsgram.service.dto.user.SignUpUserRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HomeControllerTest extends AbstractControllerTest {

    private String cookie;
    private SignUpUserRequest signUpUserRequest;

    @BeforeEach
    void setUp() {
        signUpUserRequest = createSignUpUser();

        cookie = getCookieAfterSignUpAndLogin(signUpUserRequest);
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
