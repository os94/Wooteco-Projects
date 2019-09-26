package webserver;

import http.HttpSession;

import java.util.HashMap;
import java.util.Map;

public class SessionManager {
    public static final String JSESSIONID = "JSESSIONID";

    private static final Map<String, HttpSession> sessionPool = new HashMap<>();

    public static HttpSession getSession(String jSessionId) {
        if (sessionPool.containsKey(jSessionId)) {
            return sessionPool.get(jSessionId);
        }
        throw new IllegalArgumentException("HttpSession을 꺼내는데 실패했습니다.");
    }

    public static HttpSession createSession() {
        HttpSession session = HttpSession.create();
        sessionPool.put(session.getId(), session);
        return session;
    }
}
