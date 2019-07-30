package techcourse.myblog.web.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import techcourse.myblog.domain.Article;
import techcourse.myblog.domain.User;
import techcourse.myblog.domain.repository.ArticleRepository;
import techcourse.myblog.domain.repository.CommentRepository;
import techcourse.myblog.domain.repository.UserRepository;

import static org.springframework.http.HttpHeaders.LOCATION;
import static org.springframework.test.web.reactive.server.WebTestClient.RequestBodySpec;
import static org.springframework.web.reactive.function.BodyInserters.fromFormData;

@AutoConfigureWebTestClient
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CommentControllerTests {
    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CommentRepository commentRepository;

    private static final String JSESSIONID = "JSESSIONID";
    private static final String URI_ARTICLES = "/articles";

    private User sean;
    private User pobi;
    private Article article;

    @BeforeEach
    void setUp() {
        sean = new User("sean", "sean@gmail.com", "Woowahan123!");
        pobi = new User("pobi", "pobi@gmail.com", "Woowahan123!");
        userRepository.save(sean);
        userRepository.save(pobi);

        article = new Article("title", "coverUrl", "contents");
        article.setAuthor(sean);
        articleRepository.save(article);
    }

    @Test
    void 댓글_작성() {
        statusWith(HttpMethod.GET, URI_ARTICLES + "/" + article.getId()
                , sean.getEmail(), sean.getPassword()).exchange()
                .expectStatus().isOk();

        statusWith(HttpMethod.POST, "/articles/" + article.getId() + "/comments"
                , sean.getEmail(), sean.getPassword())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(fromFormData("contents", "comment-test")
                        .with("articleId", String.valueOf(article.getId())))
                .exchange()
                .expectStatus().is3xxRedirection()
                .expectHeader().valueMatches(LOCATION, ".*/articles/" + article.getId());
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
        articleRepository.deleteAll();
        commentRepository.deleteAll();
    }

    private RequestBodySpec statusWith(HttpMethod httpMethod, String uri, String email, String password) {
        return webTestClient.method(httpMethod).uri(uri)
                .cookie(JSESSIONID, getResponseCookie(email, password).getValue());
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
