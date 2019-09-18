package http;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

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