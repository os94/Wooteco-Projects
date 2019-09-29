package controller.impl;

import http.common.HttpStatus;
import http.request.HttpRequest;
import http.response.HttpResponse;
import org.junit.jupiter.api.Test;
import utils.HttpRequestFixtureUtils;
import controller.NotSupportedHttpMethodException;

import static http.common.HeaderFields.LOCATION;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HomeControllerTest {
    private HttpRequest request;
    private HttpResponse response;

    @Test
    void NotSupportedHttpMethodException_when_doPost() {
        request = HttpRequestFixtureUtils.makeHttpRequestFixture("POST / HTTP/1.1");
        response = new HttpResponse(request);

        assertThrows(NotSupportedHttpMethodException.class, () -> new HomeController().service(request, response));
    }

    @Test
    void redirect_to_mainPage() {
        request = HttpRequestFixtureUtils.makeHttpRequestFixture("GET / HTTP/1.1");
        response = new HttpResponse(request);

        new HomeController().service(request, response);

        assertThat(response.getStatus()).isEqualByComparingTo(HttpStatus.FOUND);
        assertThat(response.getHeader(LOCATION)).isEqualTo("/index.html");
    }
}