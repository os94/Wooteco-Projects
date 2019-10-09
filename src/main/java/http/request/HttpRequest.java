package http.request;

import http.common.HttpMethod;
import http.common.HttpSession;
import http.exception.HttpRequestCreateException;
import http.exception.InvalidHttpHeaderException;
import webserver.SessionManager;
import webserver.requestresolver.RequestMapping;

import java.util.Objects;

import static http.common.HeaderFields.*;
import static webserver.SessionManager.JSESSIONID;

public class HttpRequest {
    private final RequestLine requestLine;
    private final RequestHeader requestHeader;
    private final Parameters requestBody;

    public HttpRequest(RequestLine requestLine, RequestHeader requestHeader, Parameters requestBody) {
        if (requestLine == null || requestHeader == null || requestBody == null) {
            throw new HttpRequestCreateException("Http Request 생성에 실패했습니다.");
        }
        this.requestLine = requestLine;
        this.requestHeader = requestHeader;
        this.requestBody = requestBody;
    }

    public boolean requestFile() {
        return getPath().contains(COMMA);
    }

    public boolean containsCookie(String cookieName) {
        return requestHeader.containsCookie(cookieName);
    }

    public boolean checkSessionAttribute(String sessionKey, Object sessionValue) {
        if (getSession(false) == null) {
            return false;
        }
        return getSession(true).getAttribute(sessionKey).equals(sessionValue);
    }

    public String getParameter(String parameter) {
        if (requestLine.containsParameter(parameter)) {
            return requestLine.getParameter(parameter);
        }
        if (requestBody.contains(parameter)) {
            return requestBody.getParameter(parameter);
        }
        throw new InvalidHttpHeaderException(parameter + "가 존재하지 않습니다.");
    }

    public HttpSession getSession(boolean create) {
        SessionManager sessionManager = SessionManager.getInstance();
        String jSessionId = "";
        if (containsCookie(JSESSIONID)) {
            jSessionId = getCookie(JSESSIONID);
        }
        if (sessionManager.contains(jSessionId)) {
            return sessionManager.getSession(jSessionId);
        }
        if (create) {
            HttpSession session = sessionManager.createSession();
            requestHeader.addCookie(JSESSIONID, session.getId());
            return session;
        }
        return null;
    }

    public String getCookie(String name) {
        return requestHeader.getCookie(name);
    }

    public RequestMapping getRequestMapping() {
        return new RequestMapping(getPath(), getMethod());
    }

    public String getContentTypeByAccept() {
        return getHeader(ACCEPT).split(REGEX_COMMA)[0];
    }

    public HttpMethod getMethod() {
        return requestLine.getMethod();
    }

    public String getHttpVersion() {
        return requestLine.getHttpVersion();
    }

    public String getHeader(String fieldName) {
        return requestHeader.getHeader(fieldName);
    }

    public String getPath() {
        return requestLine.getPath();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HttpRequest that = (HttpRequest) o;
        return Objects.equals(requestLine, that.requestLine) &&
                Objects.equals(requestHeader, that.requestHeader) &&
                Objects.equals(requestBody, that.requestBody);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requestLine, requestHeader, requestBody);
    }

    @Override
    public String toString() {
        return requestLine.toString() + requestHeader + requestBody;
    }
}
