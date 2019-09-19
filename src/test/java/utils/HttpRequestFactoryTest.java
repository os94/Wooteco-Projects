package utils;

import org.junit.jupiter.api.Test;
import webserver.InvalidRequestHeaderException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class HttpRequestFactoryTest {
    @Test
    void invalid_header_method() {
        String header = "GGG /index.html HTTP/1.1";
        assertThrows(InvalidRequestHeaderException.class, () -> {
            HttpRequestFactory.parsePath(header);
        });
    }

    @Test
    void invalid_header_protocol() {
        String header = "GET /index.html HHHH/1.1";
        assertThrows(InvalidRequestHeaderException.class, () -> {
            HttpRequestFactory.parsePath(header);
        });
    }

    @Test
    void invalid_header_split_length() {
        String header = "GET /index.html hello HTTP/1.1";
        assertThrows(InvalidRequestHeaderException.class, () -> {
            HttpRequestFactory.parsePath(header);
        });
    }

    @Test
    void valid_header() {
        String header = "GET /index.html HTTP/1.1";
        assertThat(HttpRequestFactory.parsePath(header)).isEqualTo("/index.html");
    }
}