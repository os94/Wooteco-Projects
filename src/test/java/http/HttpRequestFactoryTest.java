package http;

import org.junit.jupiter.api.Test;
import webserver.InvalidRequestHeaderException;

import java.io.*;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HttpRequestFactoryTest {
    private String testDirectory = "./src/test/resources/";
    @Test
    void invalid_header_method() throws FileNotFoundException {
        InputStream in = new FileInputStream(new File(testDirectory + "Invalid_Http_Header.txt"));
        assertThrows(InvalidRequestHeaderException.class, () -> {
            HttpRequestFactory.createHttpRequest(in);
        });
    }

    @Test
    void get_request_header() throws IOException {
        InputStream in = new FileInputStream(new File(testDirectory + "get_Http_Header.txt"));
        HttpRequest httprequest = HttpRequestFactory.createHttpRequest(in);

        assertThat(httprequest.isGetMethod()).isTrue();
        assertThat(httprequest.getPath()).isEqualTo("/user/create");

        Map<String, String> headerFields = httprequest.getHeaderFields();
        assertThat(headerFields.get("Host")).isEqualTo("localhost:8080");
        assertThat(headerFields.get("Connection")).isEqualTo("keep-alive");
        assertThat(headerFields.get("Accept")).isEqualTo("*/*");

        Map<String, String> dataSet = httprequest.getDataSet();
        assertThat(dataSet.get("userId")).isEqualTo("seon");
        assertThat(dataSet.get("password")).isEqualTo("password");
        assertThat(dataSet.get("name")).isEqualTo("sos");
        assertThat(dataSet.get("email")).isEqualTo("sos@sos.sos");
    }

    @Test
    void post_request_header() throws IOException {
        InputStream in = new FileInputStream(new File(testDirectory + "post_Http_Header.txt"));
        HttpRequest httprequest = HttpRequestFactory.createHttpRequest(in);

        assertThat(httprequest.isPostMethod()).isTrue();
        assertThat(httprequest.getPath()).isEqualTo("/user/create");

        Map<String, String> headerFields = httprequest.getHeaderFields();
        assertThat(headerFields.get("Host")).isEqualTo("localhost:8080");
        assertThat(headerFields.get("Connection")).isEqualTo("keep-alive");
        assertThat(headerFields.get("Accept")).isEqualTo("*/*");

        Map<String, String> dataSet = httprequest.getDataSet();
        assertThat(dataSet.get("userId")).isEqualTo("seon");
        assertThat(dataSet.get("password")).isEqualTo("password");
        assertThat(dataSet.get("name")).isEqualTo("sos");
        assertThat(dataSet.get("email")).isEqualTo("sos@sos.sos");
    }
}