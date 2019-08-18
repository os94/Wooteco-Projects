package com.woowacourse.dsgram.web.controller;

import com.woowacourse.dsgram.service.dto.user.AuthUserRequest;
import com.woowacourse.dsgram.service.dto.user.SignUpUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AbstractControllerTest {
    static int AUTO_INCREMENT = 0;

    @Autowired
    WebTestClient webTestClient;

    String getCookie(AuthUserRequest authUserRequest) {
        return webTestClient.post().uri("/api/users/login")
                .body(Mono.just(authUserRequest), AuthUserRequest.class)
                .exchange()
                .expectStatus().isOk()
                .returnResult(String.class)
                .getResponseHeaders()
                .getFirst("Set-Cookie");
    }

    WebTestClient.ResponseSpec defaultSignUp(SignUpUserRequest signUpUserRequest, boolean willIncrease) {
        if (willIncrease) {
            AUTO_INCREMENT++;
        }

        return webTestClient.post().uri("/api/users")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(signUpUserRequest), SignUpUserRequest.class)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange();
    }

    void checkExceptionMessage(WebTestClient.ResponseSpec response, String message) {
        response.expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("$.message").isEqualTo(message);

    }
}
