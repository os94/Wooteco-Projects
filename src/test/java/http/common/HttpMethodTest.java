package http.common;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpMethodTest {
    @Test
    void matches_true() {
        assertThat(HttpMethod.matchName("GET")).isTrue();
    }

    @Test
    void matches_false() {
        assertThat(HttpMethod.matchName("GIT")).isFalse();
    }
}