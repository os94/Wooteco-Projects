package webserver.controller.impl;

import db.DataBase;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.User;
import org.junit.jupiter.api.Test;
import utils.HttpRequestFixtureUtils;
import webserver.controller.NotSupportedHttpMethodException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CreateUserControllerTest {
    private HttpRequest request;
    private HttpResponse response;

    @Test
    void NotSupportedHttpMethodException_when_doGet() {
        request = HttpRequestFixtureUtils.makeHttpRequestFixture("GET /user/create HTTP/1.1");
        response = new HttpResponse(request);

        assertThrows(NotSupportedHttpMethodException.class, () -> new CreateUserController().service(request, response));
    }

    @Test
    void addUser() {
        request = HttpRequestFixtureUtils.makeHttpRequestFixture(
                "POST /user/create HTTP/1.1",
                "userId=1&name=sean&password=1234&email=sean@gmail.com");
        response = new HttpResponse(request);

        new CreateUserController().service(request, response);
        User user = DataBase.findUserById("1");

        assertThat(user).isEqualTo(new User("1", "1234", "sean", "sean@gmail.com"));
    }
}