package techcourse.myblog.web.controller;

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
import org.springframework.http.ResponseCookie;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.StatusAssertions;
import org.springframework.test.web.reactive.server.WebTestClient;
import techcourse.myblog.domain.User;
import techcourse.myblog.domain.repository.UserRepository;

import static org.springframework.web.reactive.function.BodyInserters.fromFormData;

@AutoConfigureWebTestClient
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ArticleControllerTests2 {
    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private UserRepository userRepository;

    private static final String JSESSIONID = "JSESSIONID";
    private static final String URI_ARTICLES = "/articles";
    private static int SEAN_ARTICLE_ID;

    @BeforeEach
    void 게시글_작성() {
        userRepository.save(new User("sean", "sean@gmail.com", "Woowahan123!"));
        userRepository.save(new User("pobi", "pobi@gmail.com", "Woowahan123!"));

        webTestClient.post().uri(URI_ARTICLES)
                .cookie(JSESSIONID, getResponseCookie("sean@gmail.com", "Woowahan123!").getValue())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(fromFormData("title", "title")
                        .with("coverUrl", "coverUrl")
                        .with("contents", "contents"))
                .exchange()
                .expectStatus().isFound()
                .expectBody().consumeWith(response -> {
            String path = response.getResponseHeaders().getLocation().getPath();
            int index = path.lastIndexOf("/");
            SEAN_ARTICLE_ID = Integer.parseInt(path.substring(index + 1));
        });

        webTestClient.post().uri(URI_ARTICLES)
                .cookie(JSESSIONID, getResponseCookie("pobi@gmail.com", "Woowahan123!").getValue())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(fromFormData("title", "title")
                        .with("coverUrl", "coverUrl")
                        .with("contents", "contents"))
                .exchange()
                .expectStatus().isFound();
    }

    @Test
    void 게시글_조회() {
        statusWith(HttpMethod.GET, URI_ARTICLES + "/" + SEAN_ARTICLE_ID
                , "sean@gmail.com", "Woowahan123!").isOk();
    }

    @Test
    void 게시글_수정_페이지_이동() {
        statusWith(HttpMethod.GET, URI_ARTICLES + "/" + SEAN_ARTICLE_ID + "/edit"
                , "pobi@gmail.com", "Woowahan123!")
                .is3xxRedirection();
    }

    @Test
    void 게시글_수정() {

        webTestClient.put().uri(URI_ARTICLES + "/" + SEAN_ARTICLE_ID)
                .cookie(JSESSIONID, getResponseCookie("sean@gmail.com", "Woowahan123!").getValue())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(fromFormData("title", "updated_title")
                        .with("coverUrl", "updated_coverUrl")
                        .with("contents", "updated_contents"))
                .exchange()
                .expectStatus().isFound()
                .expectHeader().valueMatches(HttpHeaders.LOCATION, ".*articles/" + SEAN_ARTICLE_ID);
    }

    @AfterEach
    void 게시글_삭제() {
        statusWith(HttpMethod.DELETE, URI_ARTICLES + "/" + SEAN_ARTICLE_ID,
                "sean@gmail.com", "Woowahan123!").isFound()
                .expectHeader().valueMatches(HttpHeaders.LOCATION, ".*/");
        userRepository.deleteAll();
    }

    private StatusAssertions statusWith(HttpMethod httpMethod, String uri, String email, String password) {
        return webTestClient.method(httpMethod).uri(uri)
                .cookie(JSESSIONID, getResponseCookie(email, password).getValue())
                .exchange().expectStatus();
    }

    private ResponseCookie getResponseCookie(String email, String password) {
        return webTestClient.post().uri("/login")
                .body(fromFormData("email", email)
                        .with("password", password))
                .exchange()
                .expectStatus().is3xxRedirection()
                .returnResult(ResponseCookie.class).getResponseCookies().getFirst(JSESSIONID);
    }
}
