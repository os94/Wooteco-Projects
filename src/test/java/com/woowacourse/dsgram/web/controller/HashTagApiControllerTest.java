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
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.document;

class HashTagApiControllerTest extends AbstractControllerTest {
    private static final String COMMON_REQUEST_URL = "/api/hashTags";
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
                .jsonPath("$..keyword").value(hasItems("#qweqwe"))
                .consumeWith(document("hashTags/get/query",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestParameters(parameterWithName("query").description("검색할 키워드")),
                        responseFields(
                                fieldWithPath("hashTags[].count").description("해시태그를 포함한 게시물 수"),
                                fieldWithPath("hashTags[].keyword").description("키워드가 포함된 해시태그")
                        )));
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
    void modifyArticleWithHashTag() {
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

    @Test
    @DisplayName("키워드로 article 검색")
    void searchForKeywordResult() {
        webTestClient.get().uri(COMMON_REQUEST_URL + "/{keyword}", "qwe")
                .header("cookie", cookie)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(document("/hashTags/get/search",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(parameterWithName("keyword").description("검색할 키워드"))));
    }
}