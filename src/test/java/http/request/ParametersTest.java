package http.request;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ParametersTest {

    @Test
    void getParameter() {
        Parameters parameters = new Parameters("test=123&sean=777");
        assertThat(parameters.getParameter("test")).isEqualTo("123");
        assertThat(parameters.getParameter("sean")).isEqualTo("777");
    }

    @Test
    void contains() {
        Parameters parameters = new Parameters("test=123&sean=777");
        assertThat(parameters.contains("test")).isTrue();
        assertThat(parameters.contains("sean")).isTrue();
    }
}