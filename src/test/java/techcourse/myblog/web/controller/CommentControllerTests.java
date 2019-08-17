package techcourse.myblog.web.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;
import techcourse.myblog.domain.model.User;
import techcourse.myblog.dto.CommentRequest;
import techcourse.myblog.dto.LoginDto;

import static org.springframework.test.web.reactive.server.WebTestClient.ResponseSpec;

@AutoConfigureWebTestClient
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class CommentControllerTests {
    @Autowired
    private WebTestClient webTestClient;

    private static final String NAME = "com";
    private static final String EMAIL = "com@gmail.com";
    private static final String PASSWORD = "Woowahan123!";
    private static final String NAME2 = "pobi";
    private static final String EMAIL2 = "pobi@gmail.com";

    private String cookie;

    @BeforeEach
    void setUp() {
        User user = new User(NAME, EMAIL, PASSWORD);
        webTestClient = WebTestClient.bindToWebHandler(exchange -> exchange
                .getSession()
                .doOnNext(webSession -> webSession.getAttributes().put("user", user)).then()).build();

        signUp(webTestClient, NAME, EMAIL, PASSWORD);
        signUp(webTestClient, NAME2, EMAIL2, PASSWORD);

        cookie = login(webTestClient, EMAIL, PASSWORD);
        writeArticle(webTestClient, "title123", "coverUrl123", "contents123", cookie);
    }

    @Test
    void 댓글_작성() {
        writeComment("contents").expectStatus().isOk();
    }

    @Test
    void 댓글_조회() {
        writeComment("contents").expectStatus().isOk();

        webTestClient.get()
                .uri("/articles/1/comments")
                .header("Cookie", cookie)
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    void 댓글_수정() {
        writeComment("contents").expectStatus().isOk();

        CommentRequest request = new CommentRequest("changed");
        webTestClient.put()
                .uri("/articles/1/comments/1")
                .header("Cookie", cookie)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(request), CommentRequest.class)
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    void 댓글_삭제() {
        writeComment("contents").expectStatus().isOk();

        webTestClient.delete()
                .uri("/articles/1/comments/1")
                .header("Cookie", cookie)
                .exchange()
                .expectStatus()
                .isOk();
    }

    private void signUp(WebTestClient webTestClient, String name, String email, String password) {
        webTestClient.post().uri("/users")
                .body(BodyInserters.fromFormData("name", name)
                        .with("email", email)
                        .with("password", password))
                .exchange();
    }

    private String login(WebTestClient webTestClient, String email, String password) {
        LoginDto request = new LoginDto(email, password);
        return webTestClient.post().uri("/login")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(request), LoginDto.class)
                .exchange()
                .expectStatus()
                .isOk()
                .returnResult(String.class)
                .getResponseHeaders()
                .getFirst("Set-Cookie");
    }

    private ResponseSpec writeArticle(WebTestClient webTestClient, String title, String coverUrl, String contents, String cookie) {
        return webTestClient.post()
                .uri("/articles")
                .header("Cookie", cookie)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters
                        .fromFormData("title", title)
                        .with("coverUrl", coverUrl)
                        .with("contents", contents))
                .exchange();
    }

    private ResponseSpec writeComment(String commentContents) {
        CommentRequest request = new CommentRequest(commentContents);
        return webTestClient.post()
                .uri("/articles/1/comments")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .header("Cookie", cookie)
                .body(Mono.just(request), CommentRequest.class)
                .exchange();
    }
}
