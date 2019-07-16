package techcourse.myblog.web;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.bind.annotation.RequestMapping;

@AutoConfigureWebTestClient
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ArticleControllerTests {
    @Autowired
    private WebTestClient webTestClient;

    private static final String URI_ARTICLES = "/articles";
    private static final String LOCATION = "location";
    private static final int ARTICLE_ID = 1;

    @BeforeEach
    void 게시글_작성_확인() {
        statusIsFoundAndMatchHeaderValue(
                HttpMethod.POST,
                URI_ARTICLES,
                LOCATION,
                ".*articles.*"
        );
    }

    @Test
    void index_페이지_조회() {
        statusIsOk(HttpMethod.GET, "/");
    }

    @Test
    void 게시글_작성_페이지_확인() {
        statusIsOk(HttpMethod.GET, "/writing");
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
        statusIsFoundAndMatchHeaderValue(
                HttpMethod.PUT,
                URI_ARTICLES + "/" + ARTICLE_ID,
                LOCATION,
                ".*articles.*"
        );
    }

    @AfterEach
    void 게시글_삭제_확인() {
        statusIsFoundAndMatchHeaderValue(
                HttpMethod.DELETE,
                URI_ARTICLES + "/" + ARTICLE_ID,
                LOCATION,
                ".*/"
        );
    }

    private void statusIsOk(HttpMethod httpMethod, String uri) {
        webTestClient.method(httpMethod)
                .uri(uri)
                .exchange()
                .expectStatus()
                .isOk();
    }

    private void statusIsFoundAndMatchHeaderValue(HttpMethod httpMethod, String uri, String name, String pattern) {
        webTestClient.method(httpMethod)
                .uri(uri)
                .exchange()
                .expectStatus()
                .isFound()
                .expectHeader().valueMatches(name, pattern);
    }
}
