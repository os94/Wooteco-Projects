package techcourse.myblog.web;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

@AutoConfigureWebTestClient
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ArticleControllerTests {

    @Autowired
    private WebTestClient webTestClient;

    private static final String URI_ARTICLES = "/articles";
    private static int ARTICLE_ID;

    @BeforeEach
    void 게시글_작성() {
        webTestClient.post()
                .uri(URI_ARTICLES)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters
                        .fromFormData("title", "title")
                        .with("coverUrl", "coverUrl")
                        .with("contents", "contents"))
                .exchange()
                .expectStatus().isFound()
                .expectBody().consumeWith(response -> {
            int index = response.getResponseHeaders().getLocation().getPath().lastIndexOf("/");
            ARTICLE_ID = Integer.parseInt(response.getResponseHeaders().getLocation().getPath().substring(index + 1));
        });
    }

    @Test
    void 게시글_조회() {
        statusIsOk(HttpMethod.GET, URI_ARTICLES + "/" + ARTICLE_ID);
    }

    @Test
    void 게시글_수정_페이지_확인() {
        statusIsOk(HttpMethod.GET, URI_ARTICLES + "/" + ARTICLE_ID + "/edit");
    }

    @Test
    void 게시글_수정_확인() {
        webTestClient.put()
                .uri(URI_ARTICLES + "/" + ARTICLE_ID)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters
                        .fromFormData("title", "updated_title")
                        .with("coverUrl", "updated_coverUrl")
                        .with("contents", "updated_contents"))
                .exchange()
                .expectStatus()
                .isFound()
                .expectHeader().valueMatches(HttpHeaders.LOCATION, ".*articles/" + ARTICLE_ID);
    }

    @AfterEach
    void 게시글_삭제() {
        webTestClient.delete()
                .uri(URI_ARTICLES + "/" + ARTICLE_ID)
                .exchange()
                .expectStatus()
                .isFound()
                .expectHeader().valueMatches(HttpHeaders.LOCATION, ".*/");
    }

    private void statusIsOk(HttpMethod httpMethod, String uri) {
        webTestClient.method(httpMethod)
                .uri(uri)
                .exchange()
                .expectStatus()
                .isOk();
    }
}
