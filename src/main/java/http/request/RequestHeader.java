package http.request;

import http.common.Cookie;
import http.common.HeaderFields;
import http.exception.InvalidHttpHeaderException;

import java.util.Arrays;
import java.util.List;

public class RequestHeader extends HeaderFields {

    public RequestHeader(List<String> headerFieldLines) {
        if (headerFieldLines == null) {
            throw new InvalidHttpHeaderException("headerFields를 생성할 수 없습니다.");
        }
        setHeaderFields(headerFieldLines);
        setCookies();
    }

    private void setHeaderFields(List<String> headerFieldLines) {
        for (String headerFieldLine : headerFieldLines) {
            String key = headerFieldLine.substring(0, headerFieldLine.indexOf(COLON));
            String value = headerFieldLine.substring(headerFieldLine.indexOf(COLON) + 2);
            this.headerFields.put(key, value);
        }
    }

    private void setCookies() {
        if (headerFields.containsKey(COOKIE)) {
            Arrays.stream(getHeader(COOKIE).split(REGEX_SEMI_COLON))
                    .map(cookieString -> new Cookie(cookieString))
                    .forEach(cookie -> cookies.add(cookie));
        }
    }

    public int getContentLength() {
        return Integer.parseInt(headerFields.getOrDefault(CONTENT_LENGTH, String.valueOf(0)));
    }
}
