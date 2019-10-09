package http.common;

import java.util.Objects;

import static http.common.HeaderFields.*;

public class Cookie {
    public static final String PATH = "path";
    public static final String DEFAULT_PATH = "/";

    private final String name;
    private final String value;

    private String path;

    public Cookie(String name, String value) {
        this.name = name;
        this.value = value;
        this.path = DEFAULT_PATH;
    }

    public Cookie(String cookie) {
        String[] nameAndValue = cookie.split(REGEX_EQUAL);
        this.name = nameAndValue[0];
        this.value = nameAndValue[1];
    }

    public boolean equalsName(String name) {
        return this.name.equals(name);
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cookie cookie = (Cookie) o;
        return Objects.equals(name, cookie.name) &&
                Objects.equals(value, cookie.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, value);
    }

    @Override
    public String toString() {
        return new StringBuilder().append(COOKIE + COLON + BLANK)
                .append(name).append(EQUAL).append(value).append(NEWLINE)
                .toString();
    }
}
