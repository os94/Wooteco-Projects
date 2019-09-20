package http;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpResponseTest {
    private String testDirectory = "./src/test/resources/";

    @Test
    public void responseRedirect() throws Exception {
        HttpResponse response = new HttpResponse(HttpStatus.FOUND);
        String data = response.sendRedirect("/index.html");

        assertThat(data.contains("HTTP/1.1 302 Found")).isTrue();
        assertThat(data.contains("Location: /index.html")).isTrue();
    }

}