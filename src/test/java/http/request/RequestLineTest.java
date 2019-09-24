package http.request;

import http.common.HttpMethod;
import http.exception.InvalidHttpHeaderException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RequestLineTest {
    private RequestLine requestLine;

    @BeforeEach
    void setUp() {
        requestLine = new RequestLine("GET /index.html?userId=123&name=sean HTTP/1.1");
    }

    @Test
    void constructor_success() {
        assertNotNull(new RequestLine("GET /index.html HTTP/1.1"));
    }

    @Test
    void constructor_with_queryString_success() {
        assertNotNull(new RequestLine("GET /index.html?userId=123&name=sean HTTP/1.1"));
    }

    @Test
    void constructor_with_invalid_method() {
        assertThrows(InvalidHttpHeaderException.class, () -> new RequestLine("GIT /index.html HTTP/1.1"));
    }

    @Test
    void constructor_with_invalid_httpVersion() {
        assertThrows(InvalidHttpHeaderException.class, () -> new RequestLine("GET /index.html HPPP/1.1"));
    }

    @Test
    void check_method() {
        assertThat(requestLine.isGetMethod()).isTrue();
        assertThat(requestLine.isPostMethod()).isFalse();
    }

    @Test
    void contains_parameter() {
        assertThat(requestLine.containsParameter("userId")).isTrue();
    }

    @Test
    void getter() {
        assertThat(requestLine.getMethod()).isEqualByComparingTo(HttpMethod.GET);
        assertThat(requestLine.getPath()).isEqualTo("/index.html");
        assertThat(requestLine.getHttpVersion()).isEqualTo("HTTP/1.1");
        assertThat(requestLine.getParameter("userId")).isEqualTo("123");
        assertThat(requestLine.getParameter("name")).isEqualTo("sean");
    }
}