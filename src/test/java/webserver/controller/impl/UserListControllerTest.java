package webserver.controller.impl;

import http.common.HttpStatus;
import http.request.HttpRequest;
import http.response.HttpResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.HttpRequestFixtureUtils;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class UserListControllerTest {
    private HttpRequest request;
    private HttpResponse response;

    @Test
    @DisplayName("로그인상태면, 사용자 목록 출력")
    void get_userList_success_when_logined() {
        request = HttpRequestFixtureUtils.makeHttpRequestFixture(
                "GET user/list HTTP/1.1",
                Arrays.asList("Cookie: logined=true;", "Accept: */*"));
        response = new HttpResponse(request);

        new UserListController().doGet(request, response);

        assertThat(response.getStatus()).isEqualByComparingTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("로그인상태가 아니면, 로그인 페이지로 이동")
    void get_userList_failed_when_not_logined() {
        request = HttpRequestFixtureUtils.makeHttpRequestFixture(
                "GET user/list HTTP/1.1",
                Arrays.asList("Cookie: logined=false;", "Accept: */*"));
        response = new HttpResponse(request);

        new UserListController().doGet(request, response);

        assertThat(response.getStatus()).isEqualByComparingTo(HttpStatus.FOUND);
        assertThat(response.getHeader("Location")).isEqualTo("/user/login.html");
    }
}