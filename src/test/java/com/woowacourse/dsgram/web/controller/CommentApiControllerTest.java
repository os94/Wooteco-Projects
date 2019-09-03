package com.woowacourse.dsgram.web.controller;

import com.woowacourse.dsgram.domain.Comment;
import com.woowacourse.dsgram.service.dto.CommentRequest;
import com.woowacourse.dsgram.service.dto.user.SignUpUserRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import reactor.core.publisher.Mono;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.document;

class CommentApiControllerTest extends AbstractControllerTest {
    private String cookie;
    private String cookie2;
    private Long articleId;
    private CommentRequest commentRequest;

    @BeforeEach
    void setUp() {
        SignUpUserRequest signUpUserRequest = createSignUpUser();
        cookie = getCookieAfterSignUpAndLogin(signUpUserRequest);
        SignUpUserRequest signUpUserRequest2 = createSignUpUser();
        cookie2 = getCookieAfterSignUpAndLogin(signUpUserRequest2);
        articleId = saveArticle(cookie, "contents");
        commentRequest = new CommentRequest("comment contents", articleId);
    }

    @Test
    @DisplayName("로그인하지 않은 유저 댓글 생성 실패")
    void notLoginUserCreateCommentFail() {
        webTestClient.post().uri("/api/comments")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(commentRequest), CommentRequest.class)
                .exchange()
                .expectStatus()
                .isFound();
    }

    @Test
    @DisplayName("내용 없는 댓글 생성 실패")
    void noContentsCreateCommentFail() {
        CommentRequest request = new CommentRequest(" ", articleId);
        webTestClient.post().uri("/api/comments")
                .header("Cookie", cookie)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(request), CommentRequest.class)
                .exchange()
                .expectStatus()
                .isBadRequest();
    }

    @Test
    @DisplayName("댓글 생성 성공")
    void createCommentSuccess() {
        webTestClient.post().uri("/api/comments")
                .header("Cookie", cookie)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(commentRequest), CommentRequest.class)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .consumeWith(document("comments/post/write",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                ));
    }

    @Test
    @DisplayName("댓글 삭제 성공")
    void deleteSuccess() {
        Long commentId = createCommentRequest(cookie);
        webTestClient.delete().uri("/api/comments/" + commentId)
                .header("Cookie", cookie)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(document("comments/delete/deleteComment",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                ));
    }

    @Test
    @DisplayName("다른 사람의 댓글 삭제 실패")
    void failDeleteOtherUsersComment() {
        Long commentId = createCommentRequest(cookie);
        webTestClient.delete().uri("/api/comments/" + commentId)
                .header("Cookie", cookie2)
                .exchange()
                .expectStatus()
                .isBadRequest();
    }

    @Test
    @DisplayName("존재하지 않는 댓글 삭제 실패")
    void deleteNotExistComment() {
        Long commentId = createCommentRequest(cookie);
        Long notExistCommentId = commentId + 1L;
        webTestClient.delete().uri("/api/comments/" + notExistCommentId)
                .header("Cookie", cookie)
                .exchange()
                .expectStatus()
                .isBadRequest();
    }

    @Test
    @DisplayName("댓글 수정 성공")
    void updateCommentSuccess() {
        Long commentId = createCommentRequest(cookie);
        CommentRequest updateRequest = new CommentRequest("updated contents", articleId);
        webTestClient.put().uri("/api/comments/" + commentId)
                .header("Cookie", cookie)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(updateRequest), CommentRequest.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(document("comments/put/update",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                ));
    }

    @Test
    @DisplayName("존재하지 않는 댓글 수정 실패")
    void failUpdateNotExistComment() {
        Long notExistCommentId = createCommentRequest(cookie) + 1L;
        CommentRequest updateRequest = new CommentRequest("updated contents", articleId);
        webTestClient.put().uri("/api/comments/" + notExistCommentId)
                .header("Cookie", cookie)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(updateRequest), CommentRequest.class)
                .exchange()
                .expectStatus()
                .isBadRequest();
    }

    @Test
    @DisplayName("다른 작성자의 댓글 수정 실패")
    void failUpdateOtherUsersComment() {
        Long commentId = createCommentRequest(cookie);
        CommentRequest updateRequest = new CommentRequest("updated contents", articleId);
        webTestClient.put().uri("/api/comments/" + commentId)
                .header("Cookie", cookie2)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(updateRequest), CommentRequest.class)
                .exchange()
                .expectStatus()
                .isBadRequest();
    }

    @Test
    @DisplayName("게시글 조회 성공")
    void getComment() {
        createCommentRequest(cookie);
        webTestClient.get().uri("/api/comments/" + articleId)
                .header("Cookie", cookie)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(document("comments/get/comments",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                ));
    }

    private Long createCommentRequest(String cookie) {
        return webTestClient.post().uri("/api/comments")
                .header("Cookie", cookie)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(commentRequest), CommentRequest.class)
                .exchange()
                .expectBody(Comment.class)
                .returnResult()
                .getResponseBody()
                .getId();
    }
}

