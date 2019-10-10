package http.common;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpSessionTest {
    private HttpSession session;

    @Test
    void setter_and_getter() {
        session = HttpSession.create();
        session.setAttribute("no", 123);
        assertThat(session.getAttribute("no")).isEqualTo(123);
    }

    @Test
    void remove_attribute() {
        session = HttpSession.create();
        session.setAttribute("no", 123);
        session.setAttribute("name", "sean");
        session.removeAttribute("no");

        assertThat(session.getAttribute("no")).isEqualTo(null);
        assertThat(session.getAttribute("name")).isEqualTo("sean");
    }

    @Test
    void invalidate() {
        session = HttpSession.create();
        session.setAttribute("no", 123);
        session.setAttribute("name", "sean");
        session.invalidate();

        assertThat(session.getAttribute("no")).isEqualTo(null);
        assertThat(session.getAttribute("name")).isEqualTo(null);
    }
}