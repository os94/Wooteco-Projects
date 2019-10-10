package http.common;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static webserver.SessionManager.JSESSIONID;

class CookieTest {
    private Cookie cookie;

    @Test
    void check_default_path() {
        cookie = new Cookie(JSESSIONID, "123123123");
        assertThat(cookie.getPath()).isEqualTo("/");
    }

    @Test
    void constructor_using_string() {
        cookie = new Cookie("JSESSIONID=123123123");
        assertThat(cookie.getName()).isEqualTo("JSESSIONID");
        assertThat(cookie.getValue()).isEqualTo("123123123");
    }

    @Test
    void equalsName() {
        cookie = new Cookie(JSESSIONID, "123123123");
        assertThat(cookie.equalsName(JSESSIONID)).isTrue();
    }
}