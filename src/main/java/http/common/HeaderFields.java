package http.common;

import http.exception.InvalidHttpHeaderException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public abstract class HeaderFields {
    private static final Logger logger = LoggerFactory.getLogger(HeaderFields.class);

    public static final String CONTENT_LENGTH = "Content-Length";
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String LOCATION = "Location";
    public static final String ACCEPT = "Accept";
    public static final String SET_COOKIE = "Set-Cookie";
    public static final String COOKIE = "Cookie";

    public static final String AMPERSAND = "&";
    public static final String EQUAL = "=";
    public static final String BLANK = " ";
    public static final String COMMA = ".";
    public static final String COLON = ":";
    public static final String SEMI_COLON = ";";
    public static final String NEWLINE = "\r\n";
    public static final String QUESTION_MARK = "?";
    public static final String REGEX_QUESTION_MARK = "\\?";
    public static final String REGEX_COMMA = "\\,";
    public static final String REGEX_SEMI_COLON = "\\;";
    public static final String REGEX_EQUAL = "\\=";

    protected final Map<String, String> headerFields = new HashMap<>();
    protected final Cookies cookies = new Cookies();

    public HeaderFields() {
    }

    public boolean containsCookie(String cookieName) {
        return cookies.contains(cookieName);
    }

    public void addHeader(String fieldName, String field) {
        headerFields.put(fieldName, field);
    }

    public void addCookie(String cookieName, String value) {
        cookies.add(new Cookie(cookieName, value));
    }

    public String getHeader(String fieldName) {
        if (headerFields.containsKey(fieldName)) {
            return headerFields.get(fieldName);
        }
        logger.error("Header에 " + fieldName + "이 존재하지않습니다.");
        throw new InvalidHttpHeaderException("Header에 " + fieldName + "이 존재하지않습니다.");
    }

    public String getCookie(String cookieName) {
        return cookies.get(cookieName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HeaderFields that = (HeaderFields) o;
        return Objects.equals(headerFields, that.headerFields) &&
                Objects.equals(cookies, that.cookies);
    }

    @Override
    public int hashCode() {
        return Objects.hash(headerFields, cookies);
    }

    @Override
    public String toString() {
        return "HeaderFields{" +
                "headerFields=" + headerFields +
                ", cookies=" + cookies +
                '}';
    }
}
