package com.woowacourse.dsgram.web.controller;

import com.woowacourse.dsgram.service.dto.article.ArticleEditRequest;
import com.woowacourse.dsgram.service.dto.user.SignUpUserRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.document;


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
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(document("articles/post/write",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                ));
    }

    @Test
    @DisplayName("게시글 1개 조회")
    void findArticle() {
        webTestClient.get().uri(COMMON_REQUEST_URL, articleId)
                .header("Cookie", cookie)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(document("articles/get/oneArticle",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                ));
    }

    @Test
    @DisplayName("페이지별 게시글 조회")
    void findAllArticle() {
        webTestClient.get().uri("/api/articles?page=0")
                .header("Cookie", cookie)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(document("articles/get/articlesPerPage",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                ));
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
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(document("articles/put/updateArticle",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                ));

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
                .isOk()
                .expectBody()
                .consumeWith(document("articles/delete/deleteArticle",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                ));

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
    @DisplayName("좋아요 조회")
    void load_like_status() {
        long count = 0;

        loadLikeStatus(count, cookie, false);
    }


    @Test
    @DisplayName("좋아요 추가 후 새로 고침")
    void like_success_and_reload() {
        long count = 0;
        likeOrDisLike(++count, cookie);
        loadLikeStatus(count, cookie, true);
    }

    @Test
    @DisplayName("좋아요 삭제 후 새로고침")
    void dislike_success_and_reload() {
        long count = 0;
        count = likeOrDisLike(++count, cookie);
        likeOrDisLike(--count, cookie);
        loadLikeStatus(count, cookie, false);
    }

    @Test
    @DisplayName("좋아요 있는 상태에서 좋아요 누르지 않은 사람이 글 확인")
    void like_success_by_other() {
        long count = 0;
        count = likeOrDisLike(++count, cookie);
        loadLikeStatus(count, anotherCookie, false);
    }

    @Test
    @DisplayName("좋아요 있는 상태에서 다른사람이 좋아요 누르고 새로고침")
    void like_by_other_user() {
        long count = 0;
        count = likeOrDisLike(++count, cookie);
        likeOrDisLike(++count, anotherCookie);
        loadLikeStatus(count, anotherCookie, true);
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

    private void loadLikeStatus(long count, String cookie, boolean likeState) {
        webTestClient.get().uri(COMMON_REQUEST_URL + "/like/status", articleId)
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
        clickLikeButton(cookie)
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$")
                .isEqualTo(count);
        return count;
    }

    @Test
    void like() {
        clickLikeButton(cookie)
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(document("like/post/likeOrDislike",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                ));
    }

    private WebTestClient.ResponseSpec clickLikeButton(String cookie) {
        return webTestClient.post().uri(COMMON_REQUEST_URL + "/like", articleId)
                .header("Cookie", cookie)
                .exchange();
    }
}
