package techcourse.myblog.web.controller;

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
public class MainControllerTests {
    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private UserRepository userRepository;

    private static final String JSESSIONID = "JSESSIONID";

    @Test
    void index_페이지_조회() {
        webTestClient.get().uri("/")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void 로그인_하지않고_게시글_작성_페이지_이동시_리다이렉트() {
        webTestClient.get().uri("/writing")
                .exchange()
                .expectStatus().is3xxRedirection()
                .expectHeader().valueMatches("Location", ".*/login");
    }

    @Test
    void 로그인후_게시글_작성_페이지_이동시_성공() {
        userRepository.save(new User("sean", "sean@gmail.com", "Woowahan123!"));

        webTestClient.get().uri("/writing")
                .cookie(JSESSIONID, getResponseCookie().getValue())
                .exchange()
                .expectStatus().isOk();

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
