package http;

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
        cookies.addCookie(new Cookie("test=123"));
        assertThat(cookies.getCookie("test")).isEqualTo("123");
    }

    @Test
    void getCookie_exception() {
        assertThrows(IllegalArgumentException.class, () -> cookies.getCookie("nonExistKey"));
    }

    @Test
    void contains() {
        cookies.addCookie(new Cookie("test=123"));
        assertThat(cookies.contains("test")).isTrue();
    }
}