package techcourse.myblog.web.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import techcourse.myblog.domain.repository.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.web.reactive.function.BodyInserters.fromFormData;

@AutoConfigureWebTestClient
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTests {
    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private UserRepository userRepository;

    private static final String JSESSIONID = "JSESSIONID";
    private long userId;

    @BeforeEach
    private void 회원가입_성공() {
        webTestClient.post().uri("/users")
                .body(fromFormData("name", "sean")
                        .with("email", "sean@gmail.com")
                        .with("password", "Woowahan123!"))
                .exchange()
                .expectStatus().isFound()
                .expectHeader().valueMatches(HttpHeaders.LOCATION, ".*/login.*");
        userId = userRepository.findUserByEmail("sean@gmail.com").getId();
    }

    @Test
    void 회원가입_페이지_이동() {
        webTestClient.get().uri("/signup")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void userList_페이지_이동() {
        webTestClient.get().uri("users")
                .cookie(JSESSIONID, getResponseCookie().getValue())
                .exchange()
                .expectStatus().isOk()
                .expectBody().consumeWith(response -> {
            String body = new String(response.getResponseBody());
            assertThat(body.contains("sean")).isTrue();
            assertThat(body.contains("sean@gmail.com")).isTrue();
        });
    }

    @Test
    void mypage_이동() {
        webTestClient.get().uri("/mypage/" + userId)
                .cookie(JSESSIONID, getResponseCookie().getValue())
                .exchange()
                .expectStatus().isOk()
                .expectBody().consumeWith(response -> {
            String body = new String(response.getResponseBody());
            assertThat(body.contains("sean")).isTrue();
            assertThat(body.contains("sean@gmail.com")).isTrue();
        });
    }

    @Test
    void mypage_edit_이동() {
        webTestClient.get().uri("/user/update/" + userId)
                .cookie(JSESSIONID, getResponseCookie().getValue())
                .exchange()
                .expectStatus().isOk()
                .expectBody().consumeWith(response -> {
            String body = new String(response.getResponseBody());
            assertThat(body.contains("sean")).isTrue();
            assertThat(body.contains("sean@gmail.com")).isTrue();
        });
    }

    @Test
    void mypage_업데이트() {
        webTestClient.put().uri("/user/update/" + userId)
                .cookie(JSESSIONID, getResponseCookie().getValue())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(fromFormData("name", "sloth"))
                .exchange()
                .expectStatus().is3xxRedirection()
                .expectHeader().valueMatches("Location", ".*/mypage/" + userId);
    }

    @AfterEach
    void 계정_삭제() {
        webTestClient.delete().uri("/user/delete/" + userId)
                .cookie(JSESSIONID, getResponseCookie().getValue())
                .exchange()
                .expectStatus().is3xxRedirection()
                .expectHeader().valueMatches("Location", ".*/.*");
    }

    private ResponseCookie getResponseCookie() {
        return webTestClient.post().uri("/login")
                .body(fromFormData("email", "sean@gmail.com")
                        .with("password", "Woowahan123!"))
                .exchange()
                .expectStatus().is3xxRedirection()
                .returnResult(ResponseCookie.class).getResponseCookies().getFirst(JSESSIONID);
    }
}
