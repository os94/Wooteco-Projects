package http.request;

import http.common.HttpMethod;
import http.exception.InvalidHttpHeaderException;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HttpRequestFactoryTest {
    private String testDirectory = "./src/test/resources/";

    @Test
    void invalid_header_method() throws FileNotFoundException {
        InputStream in = new FileInputStream(new File(testDirectory + "Invalid_Http_Header.txt"));
        assertThrows(InvalidHttpHeaderException.class, () -> {
            HttpRequestFactory.createHttpRequest(in);
        });
    }

    @Test
    void get_request_header() throws IOException {
        InputStream in = new FileInputStream(new File(testDirectory + "get_Http_Header.txt"));
        HttpRequest httpRequest = HttpRequestFactory.createHttpRequest(in);

        assertThat(httpRequest.getMethod()).isEqualByComparingTo(HttpMethod.GET);
        assertThat(httpRequest.getPath()).isEqualTo("/user/create");
        assertThat(httpRequest.getHttpVersion()).isEqualTo("HTTP/1.1");

        assertThat(httpRequest.getHeader("Host")).isEqualTo("localhost:8080");
        assertThat(httpRequest.getHeader("Connection")).isEqualTo("keep-alive");
        assertThat(httpRequest.getHeader("Accept")).isEqualTo("*/*");

        assertThat(httpRequest.getParameter("userId")).isEqualTo("seon");
        assertThat(httpRequest.getParameter("password")).isEqualTo("password");
        assertThat(httpRequest.getParameter("name")).isEqualTo("sos");
        assertThat(httpRequest.getParameter("email")).isEqualTo("sos@sos.sos");
    }

    @Test
    void post_request_header() throws IOException {
        InputStream in = new FileInputStream(new File(testDirectory + "post_Http_Header.txt"));
        HttpRequest httpRequest = HttpRequestFactory.createHttpRequest(in);

        assertThat(httpRequest.getMethod()).isEqualByComparingTo(HttpMethod.POST);
        assertThat(httpRequest.getPath()).isEqualTo("/user/create");
        assertThat(httpRequest.getHttpVersion()).isEqualTo("HTTP/1.1");

        assertThat(httpRequest.getHeader("Host")).isEqualTo("localhost:8080");
        assertThat(httpRequest.getHeader("Connection")).isEqualTo("keep-alive");
        assertThat(httpRequest.getHeader("Accept")).isEqualTo("*/*");

        assertThat(httpRequest.getParameter("userId")).isEqualTo("seon");
        assertThat(httpRequest.getParameter("password")).isEqualTo("password");
        assertThat(httpRequest.getParameter("name")).isEqualTo("sos");
        assertThat(httpRequest.getParameter("email")).isEqualTo("sos@sos.sos");
    }
}