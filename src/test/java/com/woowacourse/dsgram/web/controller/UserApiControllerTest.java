package com.woowacourse.dsgram.web.controller;


import com.woowacourse.dsgram.service.dto.user.AuthUserRequest;
import com.woowacourse.dsgram.service.dto.user.SignUpUserRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.document;
import static org.springframework.test.web.reactive.server.WebTestClient.ResponseSpec;

class UserApiControllerTest extends AbstractControllerTest {
    private static String COMMON_REQUEST_URL = "/users/{userId}/edit";

    private String myCookie;
    private String anotherCookie;
    private SignUpUserRequest signUpUserRequest;

    @BeforeEach
    void setUp() {
        signUpUserRequest = createSignUpUser();
        myCookie = getCookieAfterSignUpAndLogin(signUpUserRequest);

        signUpUserRequest = createSignUpUser();
        anotherCookie = getCookieAfterSignUpAndLogin(signUpUserRequest);
    }

    @Test
    @DisplayName("회원가입")
    void signUp() {
        SignUpUserRequest newUser = new SignUpUserRequest("whale", "whale Kim", "dophin", "whale@gmail.com");
        getResponseAfterSignUp(newUser)
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(document("users/post/signUp",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("nickName").description("가입할 회원의 닉네임(중복 불가)"),
                                fieldWithPath("userName").description("가입할 회원의 이름"),
                                fieldWithPath("password").description("가입할 회원의 비밀번호"),
                                fieldWithPath("email").description("가입할 회원의 이메일(중복 불가)")
                        )));
    }

    @Test
    void signUp_duplicatedEmail_thrown_exception() {
        SignUpUserRequest anotherUser = SignUpUserRequest.builder()
                .userName("서오상씨")
                .email(signUpUserRequest.getEmail())
                .nickName("ooooohsang")
                .password("tjdhtkd12!")
                .build();

        checkExceptionMessage(getResponseAfterSignUp(anotherUser), "이미 사용중인 이메일입니다.");
    }

    @Test
    void signUp_blankEmail_thrown_exception() {
        SignUpUserRequest anotherUser = SignUpUserRequest.builder()
                .userName("서오상씨")
                .email("")
                .nickName("os94")
                .password("tjdhtkd12!")
                .build();

        checkExceptionMessage(getResponseAfterSignUp(anotherUser), "이메일 양식");
    }

    @Test
    void signUp_InvalidEmail_thrown_exception() {
        SignUpUserRequest anotherUser = SignUpUserRequest.builder()
                .userName("서오상씨")
                .email("@@")
                .nickName("os94")
                .password("tjdhtkd12!")
                .build();

        checkExceptionMessage(getResponseAfterSignUp(anotherUser), "이메일 양식");
    }

    @Test
    void signUp_duplicatedNickName_thrown_exception() {
        SignUpUserRequest anotherUser = SignUpUserRequest.builder()
                .userName("솔로스")
                .email("anotherEmail@naver.com")
                .nickName(signUpUserRequest.getNickName())
                .password("ooollehh!")
                .build();

        checkExceptionMessage(getResponseAfterSignUp(anotherUser), "이미 사용중인 닉네임입니다.");
    }

    @Test
    void login_fail() {
        AuthUserRequest authUserRequest = new AuthUserRequest("nonexistent", "nonexistent");
        ResponseSpec response = webTestClient.post().uri("/api/users/login")
                .body(Mono.just(authUserRequest), AuthUserRequest.class)
                .exchange()
                .expectStatus().isBadRequest();

        checkExceptionMessage(response, "회원정보가 일치하지 않습니다.");
    }

    @Test
    void 회원정보_수정페이지_접근() {
        webTestClient.get().uri(COMMON_REQUEST_URL, LAST_USER_ID)
                .header("Cookie", myCookie)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void 회정정보_수정페이지_접근_비로그인() {
        webTestClient.get().uri(COMMON_REQUEST_URL, LAST_USER_ID)
                .exchange()
                .expectStatus().isFound()
                .expectHeader().valueMatches("Location", ".*/login");
    }

    @Test
    void 회정정보_수정페이지_접근_다른_사용자() {
        ResponseSpec response = webTestClient.get().uri(COMMON_REQUEST_URL, LAST_USER_ID - 1)
                .header("Cookie", anotherCookie)
                .exchange()
                .expectStatus().isBadRequest();

        checkExceptionMessage(response, "회원정보가 일치하지 않습니다.");
    }

    // TODO: 2019-08-21

    @Test
    void 회원정보_수정() {
        MultipartBodyBuilder multipartBodyBuilder =
                createMultipartBodyBuilder("whale kim", "i'm Whale!", "whales", "dolphins", "https://github.com/ep1stas1s");

        webTestClient.put()
                .uri("/api/users/{userId}", LAST_USER_ID)
                .header("Cookie", myCookie)
                .body(BodyInserters.fromObject(multipartBodyBuilder.build()))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(document("users/put/update",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                ));
    }

    @Test
    void 회원정보_일부_수정_실패_닉네임_Null() {

        MultipartBodyBuilder multipartBodyBuilder =
                createMultipartBodyBuilder("포비", "", "", "dsdsds", "");

        WebTestClient.ResponseSpec response = webTestClient.put().uri("/api/users/{userId}", LAST_USER_ID)
                .header("Cookie", myCookie)
                .body(BodyInserters.fromObject(multipartBodyBuilder.build()))
                .exchange()
                .expectStatus().isBadRequest();

        checkExceptionMessage(response, "닉네임은 2~10자");
    }

    @Test
    void 회원정보_일부_수정_실패_패스워드_Null() {

        MultipartBodyBuilder multipartBodyBuilder =
                createMultipartBodyBuilder("자손", "", "jason", "", "");

        WebTestClient.ResponseSpec response = webTestClient.put().uri("/api/users/{userId}", LAST_USER_ID)
                .header("Cookie", myCookie)
                .body(BodyInserters.fromObject(multipartBodyBuilder.build()))
                .exchange()
                .expectStatus().isBadRequest();

        checkExceptionMessage(response, "비밀번호는 4~16자");
    }

    @Test
    void 회원정보_일부_수정_실패_이름_형식_불일치() {
        MultipartBodyBuilder multipartBodyBuilder =
                createMultipartBodyBuilder("자", "", "jason", "1234", "");

        WebTestClient.ResponseSpec response = webTestClient.put().uri("/api/users/{userId}", LAST_USER_ID)
                .header("Cookie", myCookie)
                .body(BodyInserters.fromObject(multipartBodyBuilder.build()))
                .exchange()
                .expectStatus().isBadRequest();

        checkExceptionMessage(response, "이름은 2~10자");
    }

    @Test
    void 회원정보_수정_다른_사용자() {
        MultipartBodyBuilder multipartBodyBuilder =
                createMultipartBodyBuilder("김포비", "반란군", "1234", "slipp.net",
                        "intro");

        ResponseSpec response = webTestClient.put().uri("/api/users/{userId}", LAST_USER_ID - 1)
                .header("Cookie", anotherCookie)
                .body(BodyInserters.fromObject(multipartBodyBuilder.build()))
                .exchange()
                .expectStatus().isBadRequest();

        checkExceptionMessage(response, "회원정보가 일치하지 않습니다.");
    }

    @Test
    void user_다른_사용자가_탈퇴_시도() {
        webTestClient.delete().uri("/api/users/{userId}", LAST_USER_ID - 1)
                .header("Cookie", anotherCookie)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void user_탈퇴() {
        long[] articleId = new long[1];

        requestWithBodyBuilder(createMultipartBodyBuilder("contents"), HttpMethod.POST, "/api/articles", myCookie)
                .expectBody()
                .jsonPath("$")
                .value(id -> articleId[0] = Long.parseLong(id.toString()));

        webTestClient.delete().uri("/api/users/{userId}", LAST_USER_ID)
                .header("Cookie", myCookie)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(document("users/delete/withdrawal",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                ));

        webTestClient.get().uri("/api/articles/{articleId}", articleId[0])
                .header("Cookie", anotherCookie)
                .exchange()
                .expectStatus().isBadRequest();
    }


    private MultipartBodyBuilder createMultipartBodyBuilder(String userName,
                                                            String intro, String nickName,
                                                            String password, String website) {
        MultipartBodyBuilder bodyBuilder = new MultipartBodyBuilder();
        bodyBuilder.part("file", new ByteArrayResource(new byte[]{1, 2, 3, 4}) {
            @Override
            public String getFilename() {
                return "catImage.jpeg";
            }
        }, MediaType.IMAGE_JPEG);
        bodyBuilder.part("userName", userName);
        bodyBuilder.part("nickName", nickName);
        bodyBuilder.part("intro", intro);
        bodyBuilder.part("webSite", website);
        bodyBuilder.part("password", password);
        return bodyBuilder;
    }
}