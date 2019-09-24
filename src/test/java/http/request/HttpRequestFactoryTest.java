package http.request;

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
        HttpRequest httprequest = HttpRequestFactory.createHttpRequest(in);

        assertThat(httprequest.isGetMethod()).isTrue();
        assertThat(httprequest.getPath()).isEqualTo("/user/create");

        assertThat(httprequest.getHeader("Host")).isEqualTo("localhost:8080");
        assertThat(httprequest.getHeader("Connection")).isEqualTo("keep-alive");
        assertThat(httprequest.getHeader("Accept")).isEqualTo("*/*");

        assertThat(httprequest.getData("userId")).isEqualTo("seon");
        assertThat(httprequest.getData("password")).isEqualTo("password");
        assertThat(httprequest.getData("name")).isEqualTo("sos");
        assertThat(httprequest.getData("email")).isEqualTo("sos@sos.sos");
    }

    @Test
    void post_request_header() throws IOException {
        InputStream in = new FileInputStream(new File(testDirectory + "post_Http_Header.txt"));
        HttpRequest httprequest = HttpRequestFactory.createHttpRequest(in);

        assertThat(httprequest.isPostMethod()).isTrue();
        assertThat(httprequest.getPath()).isEqualTo("/user/create");

        assertThat(httprequest.getHeader("Host")).isEqualTo("localhost:8080");
        assertThat(httprequest.getHeader("Connection")).isEqualTo("keep-alive");
        assertThat(httprequest.getHeader("Accept")).isEqualTo("*/*");

        assertThat(httprequest.getData("userId")).isEqualTo("seon");
        assertThat(httprequest.getData("password")).isEqualTo("password");
        assertThat(httprequest.getData("name")).isEqualTo("sos");
        assertThat(httprequest.getData("email")).isEqualTo("sos@sos.sos");
    }
}