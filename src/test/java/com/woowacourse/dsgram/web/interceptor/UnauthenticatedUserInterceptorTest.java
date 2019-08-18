package com.woowacourse.dsgram.web.interceptor;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UnauthenticatedUserInterceptorTest {

    @Autowired
    WebTestClient webTestClient;

    @Test
    void 비로그인_상태에서_인덱스_이동_시_login페이지로_redirect() {
        webTestClient.get().uri("/")
                .exchange()
                .expectStatus().isFound()
                .expectHeader().valueMatches("Location", ".*/login");
    }

    @Test
    void 비로그인_상태에서_회원수정_시_login페이지로_redirect() {
        webTestClient.get().uri("/user/1/edit/")
                .exchange()
                .expectStatus().isFound()
                .expectHeader().valueMatches("Location", ".*/login");
    }

    @Test
    void 회원가입은_가능() {
        webTestClient.get().uri("/signup")
                .exchange()
                .expectStatus().isOk();
    }
}