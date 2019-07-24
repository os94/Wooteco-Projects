package techcourse.myblog.web.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseCookie;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import techcourse.myblog.domain.User;
import techcourse.myblog.domain.repository.UserRepository;

import static org.springframework.web.reactive.function.BodyInserters.fromFormData;

@AutoConfigureWebTestClient
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoginControllerTests {
    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private UserRepository userRepository;

    private static final String JSESSIONID = "JSESSIONID";

    @BeforeEach
    void setUp() {
        userRepository.save(new User("sean", "sean@gmail.com", "Woowahan123!"));
    }

    @Test
    void 로그인_페이지_이동() {
        webTestClient.get().uri("/login")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void 로그인_성공() {
        webTestClient.post().uri("/login")
                .body(fromFormData("email", "sean@gmail.com")
                        .with("password", "Woowahan123!"))
                .exchange()
                .expectStatus().is3xxRedirection()
                .expectHeader().valueMatches("Location", ".*/.*");
    }

    @Test
    void 로그아웃() {
        webTestClient.get().uri("/logout")
                .cookie(JSESSIONID, getResponseCookie().getValue())
                .exchange()
                .expectStatus().is3xxRedirection()
                .expectHeader().valueMatches("Location", ".*/.*");
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
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
