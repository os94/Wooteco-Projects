package http;

import http.common.HttpStatus;
import http.response.HttpResponse;
import org.junit.jupiter.api.Test;

import static http.common.HeaderFields.*;
import static org.assertj.core.api.Assertions.assertThat;

public class HttpResponseTest {
    @Test
    public void responseRedirect() throws Exception {
        HttpResponse response = new HttpResponse(request.getHttpVersion());
        response.sendRedirect("/index.html");

        assertThat(response.getStatus()).isEqualTo(HttpStatus.FOUND);
        assertThat(response.getHeader(LOCATION)).isEqualTo("/index.html");
    }

    @Test
    void addHeader() {
        HttpResponse response = new HttpResponse(request.getHttpVersion());
        response.addHeader(CONTENT_LENGTH, "123");
        assertThat(response.getHeader(CONTENT_LENGTH)).isEqualTo("123");
    }

    @Test
    void forward() {
        HttpResponse response = new HttpResponse(request.getHttpVersion());
        response.forward("./templates/index.html");
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK);
        assertThat(response.getHeader(CONTENT_TYPE)).isEqualTo("text/html;charset=utf-8");
    }
}