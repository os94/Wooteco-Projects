package http.request;

import http.common.HeaderFields;
import http.exception.InvalidHttpHeaderException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static http.common.HeaderFields.CONTENT_LENGTH;
import static http.common.HeaderFields.CONTENT_TYPE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RequestHeaderTest {
    private RequestHeader fields;

    @BeforeEach
    void setUp() {
        List<String> headerLines = Arrays.asList(
                "Content-Length: 13309",
                "Content-Type: text/html; charset=utf-8"
        );
        fields = new RequestHeader(headerLines);
    }

    @Test
    void constructor_null() {
        assertThrows(InvalidHttpHeaderException.class, () -> new RequestHeader(null));
    }

    @Test
    void constructor_and_addHeader() {
        HeaderFields fieldsByConstructor = fields;

        HeaderFields fieldsByAddHeader = new RequestHeader(new ArrayList<>());
        fieldsByAddHeader.addHeader(CONTENT_LENGTH, "13309");
        fieldsByAddHeader.addHeader(CONTENT_TYPE, "text/html; charset=utf-8");

        assertThat(fieldsByConstructor).isEqualTo(fieldsByAddHeader);
    }

    @Test
    void check_cookie() {
        fields.addCookie("testCookie", "123123");
        assertThat(fields.containsCookie("testCookie")).isTrue();
        assertThat(fields.getCookie("testCookie")).isEqualTo("123123");
    }

    @Test
    void getHeader() {
        assertThat(fields.getHeader(CONTENT_TYPE)).isEqualTo("text/html; charset=utf-8");
    }

    @Test
    void getHeader_notExist() {
        assertThrows(InvalidHttpHeaderException.class, () -> fields.getHeader("Foo-Header"));
    }

    @Test
    void getContentLength() {
        assertThat(fields.getContentLength()).isEqualTo(13309);
    }

    @Test
    void getContentLength_is_0_if_notExist() {
        RequestHeader fieldsWithoutContentLength = new RequestHeader(new ArrayList<>());
        assertThat(fieldsWithoutContentLength.getContentLength()).isEqualTo(0);
    }

    @Test
    void testToString() {
        String stringToCompare =
                "Content-Length: 13309\r\n" +
                        "Content-Type: text/html; charset=utf-8\r\n";
        assertThat(fields.toString()).isEqualTo(stringToCompare);
    }
}