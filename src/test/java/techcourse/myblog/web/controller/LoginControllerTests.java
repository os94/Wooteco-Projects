package techcourse.myblog.web.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.StatusAssertions;
import org.springframework.test.web.reactive.server.WebTestClient;

@AutoConfigureWebTestClient
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoginControllerTests {
    @Autowired
    private WebTestClient webTestClient;

    @Test
    void 로그인_페이지_이동() {
        statusWith(HttpMethod.GET, "/login").isOk();
    }

    @Test
    void 로그아웃_페이지_이동() {
        statusWith(HttpMethod.GET, "/logout").isFound();
    }

    private StatusAssertions statusWith(HttpMethod httpMethod, String uri) {
        return webTestClient.method(httpMethod)
                .uri(uri)
                .exchange()
                .expectStatus();
    }
}
