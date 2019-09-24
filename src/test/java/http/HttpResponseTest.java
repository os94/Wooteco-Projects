package http;

import http.common.HeaderFields;
import http.common.HttpStatus;
import http.exception.InvalidHttpHeaderException;
import http.request.HttpRequest;
import http.request.Parameters;
import http.request.RequestLine;
import http.response.HttpResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static http.common.HeaderFields.CONTENT_LENGTH;
import static http.common.HeaderFields.LOCATION;
import static org.assertj.core.api.Assertions.assertThat;

public class HttpResponseTest {
    private HttpResponse response;

    @BeforeEach
    void setUp() {
        RequestLine requestLine = new RequestLine("GET /index.html?userId=1 HTTP/1.1");
        Parameters requestBody = new Parameters("name=sean&password=1234");
        HeaderFields headerFields = new HeaderFields(Arrays.asList(
                "Content-Length: 13309",
                "Content-Type: text/html; charset=utf-8",
                "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8"
        ));
        HttpRequest request = new HttpRequest(requestLine, headerFields, requestBody);

        response = new HttpResponse(request);
    }

    @Test
    void check_response_Ok() {
        response.ok("123123".getBytes());
        assertThat(response.getStatus()).isEqualByComparingTo(HttpStatus.OK);
        assertThat(response.getHttpVersion()).isEqualTo("HTTP/1.1");
        assertThat(response.getHeader(CONTENT_LENGTH)).isEqualTo("6");
    }

    @Test
    void check_response_Not_Found() {
        response.notFound(new InvalidHttpHeaderException("error"));
        assertThat(response.getStatus()).isEqualByComparingTo(HttpStatus.NOT_FOUND);
        assertThat(response.getHttpVersion()).isEqualTo("HTTP/1.1");
        assertThat(response.getHeader(CONTENT_LENGTH)).isEqualTo("5");
    }

    @Test
    void check_response_Redirect() {
        response.redirect("/index.html");
        assertThat(response.getStatus()).isEqualByComparingTo(HttpStatus.FOUND);
        assertThat(response.getHttpVersion()).isEqualTo("HTTP/1.1");
        assertThat(response.getHeader(LOCATION)).isEqualTo("/index.html");
    }

    @Test
    void convert_string() {
        response.ok("123123".getBytes());
        assertThat(response.convert()).isEqualTo(
                "HTTP/1.1 200 OK\r\n" +
                        "Content-Length: 6\r\n" +
                        "Content-Type: text/html\r\n" +
                        "\r\n" +
                        "123123"
        );
    }
}
