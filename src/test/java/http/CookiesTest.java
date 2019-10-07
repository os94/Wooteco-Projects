package http;

import http.common.Cookie;
import http.common.Cookies;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CookiesTest {
    private Cookies cookies;

    @BeforeEach
    void setUp() {
        cookies = new Cookies();
    }

    @Test
    void add_and_get() {
        cookies.add(new Cookie("test=123"));
        assertThat(cookies.get("test")).isEqualTo("123");
    }

    @Test
    void getCookie_exception() {
        assertThrows(IllegalArgumentException.class, () -> cookies.get("nonExistKey"));
    }

    @Test
    void contains() {
        cookies.add(new Cookie("test=123"));
        assertThat(cookies.contains("test")).isTrue();
    }
}