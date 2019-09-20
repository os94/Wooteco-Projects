package http;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpResponseTest {
    @Test
    public void responseRedirect() throws Exception {
        HttpResponse response = new HttpResponse();
        response.sendRedirect("/index.html");

        assertThat(response.getStatus()).isEqualTo(HttpStatus.FOUND);
        assertThat(response.getHeader("Location")).isEqualTo("/index.html");
    }

    @Test
    void addHeader() {
        HttpResponse response = new HttpResponse();
        response.addHeader("Content-Length", "123");
        assertThat(response.getHeader("Content-Length")).isEqualTo("123");
    }

    @Test
    void forward() {
        HttpResponse response = new HttpResponse();
        response.forward("./templates/index.html");
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK);
        assertThat(response.getHeader("Content-Type")).isEqualTo("text/html;charset=utf-8");
    }
}