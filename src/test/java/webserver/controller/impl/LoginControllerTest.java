package webserver.controller.impl;

import http.common.HttpStatus;
import http.request.HttpRequest;
import http.response.HttpResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.HttpRequestFixtureUtils;

import static http.common.HeaderFields.LOCATION;
import static org.assertj.core.api.Assertions.assertThat;

class LoginControllerTest {
    private HttpRequest request;
    private HttpResponse response;

    @Test
    void login_success() {
        signUp("userId=sean1&name=sos&password=1234&email=sean1@gmail.com");
        request = HttpRequestFixtureUtils.makeHttpRequestFixture(
                "POST /user/login HTTP/1.1",
                "userId=sean1&password=1234");
        response = new HttpResponse(request);

        new LoginController().service(request, response);

        assertThat(response.getStatus()).isEqualByComparingTo(HttpStatus.FOUND);
        assertThat(response.getHeader(LOCATION)).isEqualTo("/index.html");
    }

    @Test
    @DisplayName("비밀번호가 일치하지 않는 경우, 로그인 실패")
    void login_failed_by_mismatch_password() {
        signUp("userId=sean2&name=sos&password=1234&email=sean2@gmail.com");
        request = HttpRequestFixtureUtils.makeHttpRequestFixture(
                "POST /user/login HTTP/1.1",
                "userId=sean2&password=7777");
        response = new HttpResponse(request);

        new LoginController().service(request, response);

        assertThat(response.getStatus()).isEqualByComparingTo(HttpStatus.FOUND);
        assertThat(response.getHeader(LOCATION)).isEqualTo("/user/login_failed.html");
    }

    @Test
    @DisplayName("사용자 Id가 존재하지않는 경우, 로그인 실패")
    void login_failed_by_not_exist_userId() {
        request = HttpRequestFixtureUtils.makeHttpRequestFixture(
                "POST /user/login HTTP/1.1",
                "userId=sean3&password=1234");
        response = new HttpResponse(request);

        new LoginController().service(request, response);

        assertThat(response.getStatus()).isEqualByComparingTo(HttpStatus.FOUND);
        assertThat(response.getHeader(LOCATION)).isEqualTo("/user/login_failed.html");
    }

    private void signUp(String requestBodyString) {
        request = HttpRequestFixtureUtils.makeHttpRequestFixture(
                "POST user/create HTTP/1.1",
                requestBodyString
        );
        response = new HttpResponse(request);
        new CreateUserController().doPost(request, response);
    }
}