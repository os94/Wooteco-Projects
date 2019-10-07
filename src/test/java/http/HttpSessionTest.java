package http;

import http.common.HttpSession;
import http.exception.HttpSessionException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
        assertThrows(HttpSessionException.class, () -> session.getAttribute("no"));
        assertThat(session.getAttribute("name")).isEqualTo("sean");
    }

    @Test
    void invalidate() {
        session = HttpSession.create();
        session.setAttribute("no", 123);
        session.setAttribute("name", "sean");

        session.invalidate();
        assertThrows(HttpSessionException.class, () -> session.getAttribute("no"));
        assertThrows(HttpSessionException.class, () -> session.getAttribute("name"));
    }
}