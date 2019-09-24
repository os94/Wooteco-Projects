package http.common;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpMethodTest {
    @Test
    void matches_true() {
        assertThat(HttpMethod.matches("GET")).isTrue();
    }

    @Test
    void matches_false() {
        assertThat(HttpMethod.matches("GIT")).isFalse();
    }
}