package webserver.controller.impl;

import http.common.HttpStatus;
import http.request.HttpRequest;
import http.response.HttpResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.HttpRequestFixtureUtils;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static http.common.HeaderFields.SET_COOKIE;
import static org.assertj.core.api.Assertions.assertThat;
import static webserver.SessionManager.JSESSIONID;

class UserListControllerTest {
    private HttpRequest request;
    private HttpResponse response;

    @Test
    @DisplayName("로그인상태면, 사용자 목록 출력")
    void get_userList_success_when_logined() {
        signUp("userId=pobi1&name=pb&password=1234&email=pobi1@gmail.com");
        login("pobi1", "1234");
        String jSessionId = extractJSessionId(response.getHeader(SET_COOKIE));

        request = HttpRequestFixtureUtils.makeHttpRequestFixture(
                "GET user/list HTTP/1.1",
                Arrays.asList("Cookie: " + JSESSIONID + "=" + jSessionId + ";", "Accept: */*"));
        response = new HttpResponse(request);
        new UserListController().doGet(request, response);

        assertThat(response.getStatus()).isEqualByComparingTo(HttpStatus.OK);
        String result = new String(response.getBody(), StandardCharsets.UTF_8);
        assertThat(result.contains("pobi1")).isTrue();
        assertThat(result.contains("pobi1@gmail.com")).isTrue();
    }

    private String extractJSessionId(String setCookieString) {
        setCookieString = setCookieString.split("\\;")[0];
        return setCookieString.split("\\=")[1];
    }

    @Test
    @DisplayName("로그인상태가 아니면, 로그인 페이지로 이동")
    void get_userList_failed_when_not_logined() {
        request = HttpRequestFixtureUtils.makeHttpRequestFixture(
                "GET user/list HTTP/1.1");
        response = new HttpResponse(request);

        new UserListController().doGet(request, response);

        assertThat(response.getStatus()).isEqualByComparingTo(HttpStatus.FOUND);
        assertThat(response.getHeader("Location")).isEqualTo("/user/login.html");
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