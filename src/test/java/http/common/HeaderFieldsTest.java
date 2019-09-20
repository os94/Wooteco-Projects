package http.common;

import http.exception.InvalidRequestHeaderException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class HeaderFieldsTest {
    private HeaderFields fields;

    @BeforeEach
    void setUp() {
        List<String> headerLines = Arrays.asList(
                "Content-Length: 13309",
                "Content-Type: text/html; charset=utf-8"
        );
        fields = new HeaderFields(headerLines);
    }

    @Test
    void constructor_null() {
        assertThrows(InvalidRequestHeaderException.class, () -> {
            new HeaderFields(null);
        });
    }

    @Test
    void constructor_and_addHeader() {
        HeaderFields fieldsByConstructor = fields;

        HeaderFields fieldsByAddHeader = new HeaderFields(new ArrayList<>());
        fieldsByAddHeader.addHeader("Content-Length", "13309");
        fieldsByAddHeader.addHeader("Content-Type", "text/html; charset=utf-8");

        assertThat(fieldsByConstructor).isEqualTo(fieldsByAddHeader);
    }

    @Test
    void getHeader() {
        assertThat(fields.getHeader("Content-Type")).isEqualTo("text/html; charset=utf-8");
    }

    @Test
    void getHeader_notExist() {
        assertThrows(InvalidRequestHeaderException.class, () -> {
            fields.getHeader("Foo-Header");
        });
    }

    @Test
    void getContentLength() {
        assertThat(fields.getContentLength()).isEqualTo(13309);
    }

    @Test
    void getContentLength_is_0_if_notExist() {
        HeaderFields fieldsWithoutContentLength = new HeaderFields(new ArrayList<>());
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