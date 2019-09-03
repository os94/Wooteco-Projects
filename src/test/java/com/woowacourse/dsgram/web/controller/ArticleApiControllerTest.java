package com.woowacourse.dsgram.web.controller;

import com.woowacourse.dsgram.service.dto.article.ArticleEditRequest;
import com.woowacourse.dsgram.service.dto.user.SignUpUserRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import reactor.core.publisher.Mono;


class ArticleApiControllerTest extends AbstractControllerTest {
    private static String COMMON_REQUEST_URL = "/api/articles/{articleId}";

    private String cookie;
    private String anotherCookie;
    private long articleId;
    private SignUpUserRequest signUpUserRequest;

    @BeforeEach
    void setUp() {
        signUpUserRequest = createSignUpUser();

        cookie = getCookieAfterSignUpAndLogin(signUpUserRequest);
        articleId = saveArticle(cookie, "contents");

        signUpUserRequest = createSignUpUser();
        anotherCookie = getCookieAfterSignUpAndLogin(signUpUserRequest);

    }

    @Test
    @DisplayName("게시글 생성 성공")
    void save() {
        requestWithBodyBuilder(createMultipartBodyBuilder("contents"), HttpMethod.POST, "/api/articles", cookie)
                .expectStatus()
                .isOk();
    }

    @Test
    @DisplayName("파일 조회 성공")
    void read() {

        webTestClient.get().uri(COMMON_REQUEST_URL + "/file", articleId)
                .header("Cookie", cookie)
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    @DisplayName("게시글 수정 성공")
    void update() {
        ArticleEditRequest articleEditRequest = new ArticleEditRequest("update contents");

        webTestClient.put().uri(COMMON_REQUEST_URL, articleId)
                .header("Cookie", cookie)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(articleEditRequest), ArticleEditRequest.class)
                .exchange()
                .expectStatus()
                .isOk();

        webTestClient.get().uri("/articles/{articleId}", articleId)
                .header("Cookie", cookie)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DisplayName("다른 사용자에 의한 게시글 수정 실패")
    void update_By_Not_Author() {
        ArticleEditRequest articleEditRequest = new ArticleEditRequest("update contents");

        webTestClient.put().uri(COMMON_REQUEST_URL, articleId)
                .header("Cookie", anotherCookie)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(articleEditRequest), ArticleEditRequest.class)
                .exchange()
                .expectStatus()
                .isBadRequest();
    }

    @Test
    @DisplayName("게시글 삭제 성공")
    void delete() {
        webTestClient.delete().uri(COMMON_REQUEST_URL, articleId)
                .header("Cookie", cookie)
                .exchange()
                .expectStatus()
                .isOk();

        webTestClient.get().uri(COMMON_REQUEST_URL, articleId)
                .header("Cookie", cookie)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    @DisplayName("다른 사용자에 의한 게시글 삭제 실패")
    void delete_by_Not_Author() {
        webTestClient.delete().uri(COMMON_REQUEST_URL, articleId)
                .header("Cookie", anotherCookie)
                .exchange()
                .expectStatus()
                .isBadRequest();
    }

    @Test
    @DisplayName("좋아요 추가")
    void like_success() {
        long count = 0;
        moveToArticle(count, cookie, false);
        count = likeOrDisLike(++count, cookie);
        moveToArticle(count, cookie, true);
    }

    @Test
    @DisplayName("좋아요 삭제")
    void dislike_success() {
        long count = 0;
        count = likeOrDisLike(++count, cookie);
        count = likeOrDisLike(--count, cookie);
        moveToArticle(count, cookie, false);
    }

    @Test
    @DisplayName("내좋아요 현황 다른사람이 확인")
    void like_success_by_other() {
        long count = 0;
        count = likeOrDisLike(++count, cookie);

        moveToArticle(count, anotherCookie, false);
    }

    @Test
    void like_by_other_user() {
        long count = 0;
        count = likeOrDisLike(++count, cookie);
        count = likeOrDisLike(++count, anotherCookie);

        moveToArticle(count, anotherCookie, true);
    }

    @Test
    @DisplayName("좋아요 누른 뒤 좋아요 리스트 조회")
    void like_list() {
        long count = 0;
        likeOrDisLike(++count, anotherCookie);

        webTestClient.get().uri(COMMON_REQUEST_URL + "/liker", articleId)
                .header("Cookie", cookie)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].nickName").isEqualTo(signUpUserRequest.getNickName())
                .jsonPath("$[0].userName").isEqualTo(signUpUserRequest.getUserName());
    }

    @Test
    @DisplayName("좋아요 누르고 다시 취소 후 좋아요 리스트 조회")
    void like_list_zero() {
        long count = 0;
        likeOrDisLike(++count, anotherCookie);
        likeOrDisLike(--count, anotherCookie);

        webTestClient.get().uri(COMMON_REQUEST_URL + "/liker", articleId)
                .header("Cookie", cookie)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$").isEmpty();
    }

    private void moveToArticle(long count, String cookie, boolean likeState) {
        webTestClient.get().uri(COMMON_REQUEST_URL, articleId)
                .header("Cookie", cookie)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.countOfLikes")
                .isEqualTo(count)
                .jsonPath("$.likeState")
                .isEqualTo(likeState);
    }

    private long likeOrDisLike(long count, String cookie) {
        webTestClient.post().uri(COMMON_REQUEST_URL + "/like", articleId)
                .header("Cookie", cookie)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$")
                .isEqualTo(count);
        return count;
    }
}
