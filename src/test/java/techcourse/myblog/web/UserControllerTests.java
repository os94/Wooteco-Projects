package techcourse.myblog.web;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.StatusAssertions;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureWebTestClient
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTests {
    @Autowired
    private WebTestClient webTestClient;

    private static int ID;

    @BeforeEach
    @ParameterizedTest
    @CsvSource({"sean, sean@gmail.com, Woowahan123!"})
    void 회원가입_성공(String name, String email, String password) {
        webTestClient.post()
                .uri("/users")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters
                        .fromFormData("name", name)
                        .with("email", email)
                        .with("password", password))
                .exchange()
                .expectStatus().isFound()
                .expectHeader().valueMatches(HttpHeaders.LOCATION, ".*login.*");
    }

    @Test
    void userList_페이지_이동_확인() {
        statusWith(HttpMethod.GET, "users")
                .isOk()
                .expectBody().consumeWith(response -> {
            String body = new String(response.getResponseBody());
            assertThat(body.contains("sean")).isTrue();
            assertThat(body.contains("sean@gmail.com")).isTrue();
        });
    }

    @Test
    void mypage_이동_확인() {
        statusWith(HttpMethod.GET, "/mypage/" + ID)
                .isOk()
                .expectBody().consumeWith(response -> {
            String body = new String(response.getResponseBody());
            assertThat(body.contains("sean")).isTrue();
            assertThat(body.contains("sean@gmail.com")).isTrue();
        });
    }

    @AfterEach
    void 계정_삭제() {
        statusWith(HttpMethod.DELETE, "/user/delete/" + ID)
                .isFound()
                .expectHeader().valueMatches(HttpHeaders.LOCATION, ".*/");

    }

    private StatusAssertions statusWith(HttpMethod httpMethod, String uri) {
        return webTestClient.method(httpMethod)
                .uri(uri)
                .exchange()
                .expectStatus();
    }
}
