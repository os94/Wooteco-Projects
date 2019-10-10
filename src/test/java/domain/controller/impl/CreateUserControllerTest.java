package domain.controller.impl;

import domain.controller.NotSupportedHttpMethodException;
import domain.db.DataBase;
import domain.model.User;
import http.common.HttpStatus;
import http.request.HttpRequest;
import http.response.HttpResponse;
import org.junit.jupiter.api.Test;
import utils.HttpRequestFixtureUtils;

import static http.common.HeaderFields.LOCATION;
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
    void addUser() throws Exception {
        request = HttpRequestFixtureUtils.makeHttpRequestFixture(
                "POST /user/create HTTP/1.1",
                "userId=sos&name=sean&password=1234&email=sean@gmail.com");
        response = new HttpResponse(request);

        new CreateUserController().service(request, response).render(request, response);
        User user = DataBase.findUserById("sos");

        assertThat(user).isEqualTo(new User("sos", "1234", "sean", "sean@gmail.com"));
        assertThat(response.getStatus()).isEqualByComparingTo(HttpStatus.FOUND);
        assertThat(response.getHeader(LOCATION)).isEqualTo("/index.html");
    }
}