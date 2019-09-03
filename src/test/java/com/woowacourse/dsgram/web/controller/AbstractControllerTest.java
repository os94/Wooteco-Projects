package com.woowacourse.dsgram.web.controller;

import com.woowacourse.dsgram.service.dto.user.AuthUserRequest;
import com.woowacourse.dsgram.service.dto.user.SignUpUserRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;

import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.reactive.server.WebTestClient.ResponseSpec;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(RestDocumentationExtension.class)
class AbstractControllerTest {
    static int LAST_USER_ID = 0;

    @LocalServerPort
    private int port;

    WebTestClient webTestClient;

    @BeforeEach
    void setUp(RestDocumentationContextProvider restDocumentation) {
        webTestClient = WebTestClient.bindToServer()
                .baseUrl("http://localhost:" + port)
                .filter(documentationConfiguration(restDocumentation))
                .build();
    }

    ResponseSpec getResponseAfterSignUp(SignUpUserRequest signUpUserRequest) {
        return webTestClient.post().uri("/api/users")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(signUpUserRequest), SignUpUserRequest.class)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange();

    }

    SignUpUserRequest createSignUpUser() {
        return SignUpUserRequest.builder()
                .email(LAST_USER_ID + "success@gmail.com")
                .userName("success")
                .nickName(LAST_USER_ID + "success")
                .password("1234")
                .build();
    }

    void checkExceptionMessage(ResponseSpec response, String message) {
        response.expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("$.message").isEqualTo(message);

    }

    ResponseSpec requestWithBodyBuilder(MultipartBodyBuilder bodyBuilder, HttpMethod requestMethod, String requestUri, String sessionCookie) {
        return webTestClient.method(requestMethod)
                .uri(requestUri)
                .header("Cookie", sessionCookie)
                .body(BodyInserters.fromObject(bodyBuilder.build()))
                .exchange();
    }

    Long saveArticle(String cookie, String contents) {
        long[] articleId = new long[1];
        requestWithBodyBuilder(createMultipartBodyBuilder(contents), HttpMethod.POST, "/api/articles", cookie)
                .expectBody()
                .jsonPath("$")
                .value(id -> articleId[0] = Long.parseLong(id.toString()));

        return articleId[0];
    }

    MultipartBodyBuilder createMultipartBodyBuilder(String contents) {
        MultipartBodyBuilder bodyBuilder = new MultipartBodyBuilder();
        bodyBuilder.part("file", new ByteArrayResource(new byte[]{1, 2, 3, 4}) {
            @Override
            public String getFilename() {
                return "catImage.jpeg";
            }
        }, MediaType.IMAGE_JPEG);
        bodyBuilder.part("contents", contents);
        bodyBuilder.part("hashtag", "hashtag");
        return bodyBuilder;
    }

    ResponseSpec requestUserFeed(String nickName, String cookie) {
        return webTestClient.get().uri("/users/{nickName}", nickName)
                .header("cookie", cookie)
                .exchange()
                .expectStatus().isOk();
    }


    String getCookieAfterSignUpAndLogin(SignUpUserRequest userInfo) {
        getResponseAfterSignUp(userInfo);
        LAST_USER_ID++;
        return getCookieAfterLogin(new AuthUserRequest(userInfo.getEmail(), userInfo.getPassword()));
    }

    private String getCookieAfterLogin(AuthUserRequest authUserRequest) {
        return webTestClient.post().uri("/api/users/login")
                .body(Mono.just(authUserRequest), AuthUserRequest.class)
                .exchange()
                .expectStatus().isOk()
                .returnResult(String.class)
                .getResponseHeaders()
                .getFirst("Set-Cookie");
    }

}
