package techcourse.myblog.web;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

@AutoConfigureWebTestClient
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MainControllerTests {
    @Autowired
    private WebTestClient webTestClient;

    private static final Logger log = LoggerFactory.getLogger(MainControllerTests.class);

    @Test
    void index_페이지_조회() {
        log.trace("A TRACE Message");
        log.debug("A DEBUG Message");
        log.info("An INFO Message");
        log.warn("A WARN Message");
        log.error("An ERROR Message");

        statusIsOk(HttpMethod.GET, "/");
    }

    @Test
    void 게시글_작성_페이지_이동() {
        statusIsOk(HttpMethod.GET, "/writing");
    }

    @Test
    void 로그인_페이지_이동() {
        statusIsOk(HttpMethod.GET, "/login");
    }

    @Test
    void 회원가입_페이지_이동() {
        statusIsOk(HttpMethod.GET, "/signup");
    }

    private void statusIsOk(HttpMethod httpMethod, String uri) {
        webTestClient.method(httpMethod)
                .uri(uri)
                .exchange()
                .expectStatus()
                .isOk();
    }
}
