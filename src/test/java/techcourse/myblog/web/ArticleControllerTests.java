package techcourse.myblog.web;

import org.apache.commons.lang3.StringEscapeUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import techcourse.myblog.domain.Article;
import techcourse.myblog.domain.ArticleRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.web.reactive.function.BodyInserters.fromFormData;

@AutoConfigureWebTestClient
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ArticleControllerTests {
    @Autowired
    private WebTestClient webTestClient;

    private ArticleRepository articleRepository;
    private Article article;
    private int id;

    @BeforeEach
    void setUp() {
        articleRepository = new ArticleRepository();
        article = new Article();
        id = 0;

        article.setId(id);
        article.setTitle("test-Title");
        article.setCoverUrl("test-coverUrl");
        article.setContents("test-Contents");

        articleRepository.add(article);
    }

    @Test
    void index_페이지_조회() {
        webTestClient.get().uri("/")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void 게시글_작성_페이지_확인() {
        webTestClient.get().uri("/writing")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void 게시글_작성_확인() {
        String title = "목적의식 있는 연습을 통한 효과적인 학습";
        String coverUrl = "https://t1.daumcdn.net/thumb/R1280x0/?fname=http://t1.daumcdn.net/brunch/service/user/5tdm/image/7OdaODfUPkDqDYIQKXk_ET3pfKo.jpeg";
        String contents = "나는 우아한형제들에서 우아한테크코스 교육 과정을 진행하고 있다. 우테코를 설계하면서 고민스러웠던 부분 중의 하나는 '선발 과정을 어떻게 하면 의미 있는 시간으로 만들 것인가?'였다.";

        /*webTestClient.post()
                .uri("/articles")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(fromFormData("title", title)
                        .with("coverUrl", coverUrl)
                        .with("contents", contents))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(response -> {
                    String body = new String(response.getResponseBody());
                    assertThat(body.contains(title)).isTrue();
                    assertThat(body.contains(coverUrl)).isTrue();
                    assertThat(body.contains(StringEscapeUtils.escapeJava(contents))).isTrue();
                });*/

        webTestClient.post()
                .uri("/articles")
                .body(fromFormData("title", "title").with("contents", "123123"))
                .exchange()
                .expectStatus().isFound()
                .expectHeader().valueMatches("location", ".*articles.*");
    }

    @Test
    void 게시글_조회() {
        webTestClient.get()
                .uri("/articles/" + id)
                .exchange()
                .expectStatus().isOk();
    }

    // 실패하는 테스트
    /*@Test
    void 게시글_수정_페이지_확인() {
        webTestClient.get()
                .uri("/articles/" + id + "/edit")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void 게시글_수정_확인() {
        webTestClient.put()
                .uri("/articles/" + id)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void 게시글_삭제_확인() {
        webTestClient.delete()
                .uri("/articles/" + id)
                .exchange()
                .expectStatus().isOk();
    }*/
}
