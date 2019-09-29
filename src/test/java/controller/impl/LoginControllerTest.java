package controller.impl;

import http.common.HttpStatus;
import http.request.HttpRequest;
import http.response.HttpResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.HttpRequestFixtureUtils;

import static http.common.HeaderFields.LOCATION;
import static http.common.HeaderFields.SET_COOKIE;
import static org.assertj.core.api.Assertions.assertThat;
import static webserver.SessionManager.JSESSIONID;

class LoginControllerTest {
    private HttpRequest request;
    private HttpResponse response;

    @Test
    @DisplayName("로그인 성공시, Status/Location/Cookie 확인")
    void login_success() {
        signUp("userId=sean1&name=sos&password=1234&email=sean1@gmail.com");
        login("sean1", "1234");

        assertThat(response.getStatus()).isEqualByComparingTo(HttpStatus.FOUND);
        assertThat(response.getHeader(LOCATION)).isEqualTo("/index.html");
        assertThat(response.getHeader(SET_COOKIE).contains(JSESSIONID)).isTrue();
    }

    @Test
    @DisplayName("비밀번호가 일치하지 않는 경우, 로그인 실패")
    void login_failed_by_mismatch_password() {
        signUp("userId=sean2&name=sos&password=1234&email=sean2@gmail.com");
        login("sean2", "7777");

        assertThat(response.getStatus()).isEqualByComparingTo(HttpStatus.FOUND);
        assertThat(response.getHeader(LOCATION)).isEqualTo("/user/login_failed.html");
    }

    @Test
    @DisplayName("사용자 Id가 존재하지않는 경우, 로그인 실패")
    void login_failed_by_not_exist_userId() {
        login("sean3", "1234");

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

    private void login(String userId, String password) {
        request = HttpRequestFixtureUtils.makeHttpRequestFixture(
                "POST /user/login HTTP/1.1",
                "userId=" + userId + "&password=" + password);
        response = new HttpResponse(request);
        new LoginController().doPost(request, response);
    }
}
