package http.response;

import http.common.HttpStatus;
import http.request.HttpRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.HttpRequestFixtureUtils;

import static http.common.HeaderFields.CONTENT_LENGTH;
import static http.common.HeaderFields.LOCATION;
import static org.assertj.core.api.Assertions.assertThat;

public class HttpResponseTest {
    private HttpResponse response;

    @BeforeEach
    void setUp() {
        HttpRequest request = HttpRequestFixtureUtils.makeHttpRequestFixture("GET /index.html HTTP/1.1");
        response = new HttpResponse(request);
    }

    @Test
    void check_response_Ok() {
        response.ok("123123".getBytes());
        assertThat(response.getStatus()).isEqualByComparingTo(HttpStatus.OK);
        assertThat(response.getHttpVersion()).isEqualTo("HTTP/1.1");
        assertThat(response.getHeader(CONTENT_LENGTH)).isEqualTo(String.valueOf("123123".length()));
    }

    @Test
    void check_response_Redirect() {
        response.redirect("/index.html");
        assertThat(response.getStatus()).isEqualByComparingTo(HttpStatus.FOUND);
        assertThat(response.getHttpVersion()).isEqualTo("HTTP/1.1");
        assertThat(response.getHeader(LOCATION)).isEqualTo("/index.html");
    }

    @Test
    void check_response_Not_Found() {
        response.notFound("error");
        assertThat(response.getStatus()).isEqualByComparingTo(HttpStatus.NOT_FOUND);
        assertThat(response.getHttpVersion()).isEqualTo("HTTP/1.1");
        assertThat(response.getHeader(CONTENT_LENGTH)).isEqualTo(String.valueOf("error".length()));
    }

    @Test
    void check_response_Internal_Server_Error() {
        response.internalServerError("error");
        assertThat(response.getStatus()).isEqualByComparingTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(response.getHttpVersion()).isEqualTo("HTTP/1.1");
        assertThat(response.getHeader(CONTENT_LENGTH)).isEqualTo(String.valueOf("error".length()));
    }

    @Test
    void convert_string() {
        String stringToCompare =
                "HTTP/1.1 200 OK\r\n" +
                        "Content-Length: 6\r\n" +
                        "Content-Type: */*\r\n" +
                        "\r\n" +
                        "123123";
        response.ok("123123".getBytes());
        assertThat(response.convert()).isEqualTo(stringToCompare);
    }
}
