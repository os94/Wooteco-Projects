package com.woowacourse.dsgram.web.controller;

import com.woowacourse.dsgram.service.dto.user.SignUpUserRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ArticleControllerTest extends AbstractControllerTest {

    private String cookie;
    private SignUpUserRequest signUpUserRequest;

    @BeforeEach
    void setUp() {
        signUpUserRequest = createSignUpUser();
        cookie = getCookieAfterSignUpAndLogin(signUpUserRequest);
    }

    @Test
    void 로그인후_글작성시_article_edit_페이지로_이동_성공() {
        webTestClient.get()
                .uri("/articles/writing")
                .header("cookie", cookie)
                .exchange()
                .expectStatus()
                .isOk();
    }
}
