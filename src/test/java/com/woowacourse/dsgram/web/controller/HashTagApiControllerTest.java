package com.woowacourse.dsgram.web.controller;

import com.woowacourse.dsgram.service.dto.article.ArticleEditRequest;
import com.woowacourse.dsgram.service.dto.user.SignUpUserRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.hamcrest.Matchers.hasItems;

class HashTagApiControllerTest extends AbstractControllerTest {
    private static final String COMMON_REQUEST_URL = "/api/hashTag";
    private static final String CONTENTS = "#qwe#qweqwe#qwe#asd#zxc#qw";

    private String cookie;
    private long articleId;

    @BeforeEach
    void setUp() {
        SignUpUserRequest signUpUserRequest = createSignUpUser();

        cookie = getCookieAfterSignUpAndLogin(signUpUserRequest);
        articleId = saveArticle(cookie, CONTENTS);
    }

    @Test
    @DisplayName("keyword 로 hashTag 검색")
    void searchArticleByHashTag() {
        searchForKeyword("?query=qw")
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$..keyword").value(hasItems("#qw"))
                .jsonPath("$..keyword").value(hasItems("#qwe"))
                .jsonPath("$..keyword").value(hasItems("#qweqwe"));
    }

    @Test
    @DisplayName("글_작성시_hashTag_저장_확인")
    void searchHashTag() {
        searchForKeyword("/qwe")
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.content..contents").value(hasItems(CONTENTS));
    }

    @Test
    @DisplayName("글 수정 시 해시태그 수정")
    void name() {
        ArticleEditRequest articleEditRequest = new ArticleEditRequest("#캄보디아");

        webTestClient.put().uri("/api/articles/{articleId}", articleId)
                .header("Cookie", cookie)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(articleEditRequest), ArticleEditRequest.class)
                .exchange()
                .expectStatus()
                .isOk();

        searchForKeyword("/캄보디아")
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.content..contents").value(hasItems(articleEditRequest.getContents()));
    }

    private WebTestClient.ResponseSpec searchForKeyword(String keyword) {
        return webTestClient.get().uri(COMMON_REQUEST_URL + keyword)
                .header("cookie", cookie)
                .exchange();
    }
}