package techcourse.myblog.web;

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
public class UserControllerTests {

    @Autowired
    private WebTestClient webTestClient;

    private static int USER_ID;

    @Test
    void 회원가입_페이지_이동_확인() {
        statusIsOk(HttpMethod.GET, "/signup");
    }

    @Test
    void 회원가입_확인() {
        webTestClient.post()
                .uri("/users")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters
                        .fromFormData("name", "sean")
                        .with("email", "sean@gmail.com")
                        .with("password", "Woowahan123!"))
                .exchange()
                .expectStatus().isFound()
                .expectHeader().valueMatches(HttpHeaders.LOCATION, ".*login.*");
    }

    private void statusIsOk(HttpMethod httpMethod, String uri) {
        webTestClient.method(httpMethod)
                .uri(uri)
                .exchange()
                .expectStatus()
                .isOk();
    }
}
