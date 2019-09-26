package http;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Cookies {
    private final List<Cookie> cookies = new ArrayList<>();

    public Cookies() {
    }

    public void addCookie(Cookie cookie) {
        cookies.add(cookie);
    }

    public String getCookie(String name) {
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
        return "Cookies{" +
                "cookies=" + cookies +
                '}';
    }
}
