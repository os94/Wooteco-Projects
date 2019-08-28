package com.woowacourse.dsgram.web.controller;

import com.woowacourse.dsgram.service.dto.FollowRequest;
import com.woowacourse.dsgram.service.dto.user.SignUpUserRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import reactor.core.publisher.Mono;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.reactive.server.WebTestClient.ResponseSpec;

public class FeedApiControllerTest extends AbstractControllerTest {
    private String cookie;
    private SignUpUserRequest signUpUserRequest;
    private SignUpUserRequest signUpUserRequest2;
    private FollowRequest followRequest;


    @BeforeEach
    void setUp() {
        signUpUserRequest = createSignUpUser();
        cookie = getCookieAfterSignUpAndLogin(signUpUserRequest);
        signUpUserRequest2 = createSignUpUser();
        getCookieAfterSignUpAndLogin(signUpUserRequest2);
        followRequest = new FollowRequest(signUpUserRequest.getNickName(), signUpUserRequest2.getNickName());
    }

    @Test
    void 팔로우_성공() {
       followOrUnfollow(followRequest)
                .expectStatus().isOk();

        requestUserFeed(signUpUserRequest2.getNickName(), cookie)
                .expectBody()
                .consumeWith(res -> {
                    String body = new String(Objects.requireNonNull(res.getResponseBody()));
                    assertThat(body.contains("언팔로우")).isTrue();
                });
    }

    @Test
    void 팔로우_실패_없는_유저() {
        followRequest = new FollowRequest(signUpUserRequest.getNickName(), "exception");
        followOrUnfollow(followRequest)
                .expectStatus().isBadRequest();
    }

    @Test
    void 언팔로우_성공() {
        followOrUnfollow(followRequest)
                .expectStatus().isOk();

        followOrUnfollow(followRequest)
                .expectStatus().isOk();

        requestUserFeed(signUpUserRequest2.getNickName(), cookie)
                .expectBody()
                .consumeWith(res -> {
                    String body = new String(Objects.requireNonNull(res.getResponseBody()));
                    assertThat(body.contains("팔로우")).isTrue();
                    assertThat(body.contains("언팔로우")).isFalse();
                });
    }

    private ResponseSpec followOrUnfollow(FollowRequest followRequest) {
        return webTestClient.post().uri("/follow")
                .header("cookie", this.cookie)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(followRequest), FollowRequest.class)
                .exchange();
    }
}
