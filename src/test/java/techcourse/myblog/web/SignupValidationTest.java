package techcourse.myblog.web;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import static org.assertj.core.api.Assertions.assertThat;
import static techcourse.myblog.dto.UserDto.*;

@AutoConfigureWebTestClient
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SignupValidationTest {
    @Autowired
    private WebTestClient webTestClient;

    @Test
    void name이_2자_미만인_경우() {
        checkValidationWith(NAME_LENGTH_ERROR, "s", "sean@gmail.com", "Woowahan123!");
    }

    @Test
    void name이_10자_초과인_경우() {
        checkValidationWith(NAME_LENGTH_ERROR, "qwerasdfzxc", "sean@gmail.com", "Woowahan123!");
    }

    @Test
    void name에_숫자가_포함된_경우() {
        checkValidationWith(NAME_FORMAT_ERROR, "sean123", "sean@gmail.com", "Woowahan123!");
    }

    @Test
    void name에_특수문자가_포함된_경우() {
        checkValidationWith(NAME_FORMAT_ERROR, "sean!", "sean@gmail.com", "Woowahan123!");
    }

    @Test
    void name에_공백이_입력된_경우() {
        checkValidationWith(NAME_BLANK_ERROR, " ", "sean@gmail.com", "Woowahan123!");
    }

    @Test
    void email_양식이_잘못된_경우() {
        checkValidationWith(EMAIL_FORMAT_ERROR, "sean!", "sean_gmail.com", "Woowahan123!");
    }

    @Test
    void email이_공백인_경우() {
        checkValidationWith(EMAIL_BLANK_ERROR, "sean!", "", "Woowahan123!");
    }

    @Test
    void password에_소문자가_포함되지_않은_경우() {
        checkValidationWith(PASSWORD_FORMAT_ERROR, "sean!", "sean@gmail.com", "WOOWAHAN123!");
    }

    @Test
    void password에_대문자가_포함되지_않은_경우() {
        checkValidationWith(PASSWORD_FORMAT_ERROR, "sean!", "sean@gmail.com", "woowahan123!");
    }

    @Test
    void password에_숫자가_포함되지_않은_경우() {
        checkValidationWith(PASSWORD_FORMAT_ERROR, "sean!", "sean@gmail.com", "Woowahan!");
    }

    @Test
    void password에_특수문자가_포함되지_않은_경우() {
        checkValidationWith(PASSWORD_FORMAT_ERROR, "sean!", "sean@gmail.com", "Woowahan123");
    }

    @Test
    void password에_공백이_입력된_경우() {
        checkValidationWith(PASSWORD_BLANK_ERROR, "sean!", "sean@gmail.com", " ");
    }

    private void checkValidationWith(String errorMessage, String name, String email, String password) {
        webTestClient.post()
                .uri("/users")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters
                        .fromFormData("name", name)
                        .with("email", email)
                        .with("password", password))
                .exchange()
                .expectStatus().isOk()
                .expectBody().consumeWith(response -> {
            String body = new String(response.getResponseBody());
            assertThat(body.contains(errorMessage)).isTrue();
        });
    }
}
