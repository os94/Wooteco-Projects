package http.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static http.common.HeaderFields.*;

public class Cookies {
    private final List<Cookie> cookies = new ArrayList<>();

    public Cookies() {
    }

    public void add(Cookie cookie) {
        cookies.add(cookie);
    }

    public String get(String name) {
        return cookies.stream()
                .filter(cookie -> cookie.equalsName(name))
                .map(Cookie::getValue)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(name + "이 Cookie에 존재하지 않습니다."));
    }

    public boolean contains(String name) {
        return cookies.stream()
                .anyMatch(cookie -> cookie.equalsName(name));
    }

    public String toSetCookieString() {
        StringBuilder sb = new StringBuilder();
        for (Cookie cookie : cookies) {
            sb.append(SET_COOKIE).append(COLON + BLANK)
                    .append(cookie.getName()).append(EQUAL).append(cookie.getValue()).append(SEMI_COLON + BLANK)
                    .append(cookie.PATH).append(EQUAL).append(cookie.getPath()).append(SEMI_COLON + NEWLINE);
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cookies cookies1 = (Cookies) o;
        return Objects.equals(cookies, cookies1.cookies);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cookies);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        cookies.forEach(cookie -> sb.append(cookie));
        return sb.toString();
    }
}
