package http.common;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CookieTest {
    @Test
    void cookie() {
        Cookie cookie = new Cookie("JSESSIONID=123123123");
        assertThat(cookie.getName()).isEqualTo("JSESSIONID");
        assertThat(cookie.getValue()).isEqualTo("123123123");
    }
}