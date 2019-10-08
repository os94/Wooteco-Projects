package webserver;

import http.common.HttpSession;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SessionManager {
    public static final String JSESSIONID = "JSESSIONID";

    private static final Map<String, HttpSession> sessionPool = new ConcurrentHashMap<>();

    private SessionManager() {
    }

    private static class LazyHolder {
        private static final SessionManager INSTANCE = new SessionManager();
    }

    public static SessionManager getInstance() {
        return LazyHolder.INSTANCE;
    }

    public HttpSession getSession(String jSessionId) {
        if (sessionPool.containsKey(jSessionId)) {
            return sessionPool.get(jSessionId);
        }
        throw new IllegalArgumentException("HttpSession을 꺼내는데 실패했습니다.");
    }

    public HttpSession createSession() {
        HttpSession session = HttpSession.create();
        sessionPool.put(session.getId(), session);
        return session;
    }

    public boolean contains(String jSessionId) {
        return sessionPool.containsKey(jSessionId);
    }
}
