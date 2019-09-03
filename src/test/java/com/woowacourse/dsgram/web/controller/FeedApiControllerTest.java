package com.woowacourse.dsgram.web.controller;

import com.woowacourse.dsgram.service.dto.follow.FollowRequest;
import com.woowacourse.dsgram.service.dto.user.SignUpUserRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import reactor.core.publisher.Mono;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.document;
import static org.springframework.test.web.reactive.server.WebTestClient.ResponseSpec;

class FeedApiControllerTest extends AbstractControllerTest {
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
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(document("follow/post/followAndUnfollow",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                ));

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
        return webTestClient.post().uri("/api/follow")
                .header("cookie", this.cookie)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(followRequest), FollowRequest.class)
                .exchange();
    }

    @Test
    void followerList() {
        followOrUnfollow(followRequest)
                .expectStatus().isOk();

        webTestClient.get().uri("/api/followers/{nickName}", signUpUserRequest2.getNickName())
                .header("cookie", this.cookie)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectBody()
                .consumeWith(document("follow/get/followers",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                ));
    }

    @Test
    void followingList() {
        followOrUnfollow(followRequest)
                .expectStatus().isOk();

        webTestClient.get().uri("/api/followings/{nickName}", signUpUserRequest.getNickName())
                .header("cookie", this.cookie)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectBody()
                .consumeWith(document("follow/get/following",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                ));
    }
}
