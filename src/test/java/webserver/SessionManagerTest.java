package webserver;

import http.common.HttpSession;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SessionManagerTest {
    @Test
    void create_and_get() {
        HttpSession session = SessionManager.getInstance().createSession();
        assertThat(SessionManager.getInstance().getSession(session.getId())).isEqualTo(session);
    }

    @Test
    void getSession_exception() {
        assertThrows(IllegalArgumentException.class, () -> SessionManager.getInstance().getSession("notExistSessionId"));
    }
}
