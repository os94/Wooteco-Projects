package http;

import java.util.Objects;

import static http.common.HeaderFields.REGEX_EQUAL;

public class Cookie {
    private final String name;
    private final String value;

    public Cookie(String cookie) {
        String[] nameAndValue = cookie.split(REGEX_EQUAL);
        this.name = nameAndValue[0];
        this.value = nameAndValue[1];
    }

    public boolean equalsName(String name) {
        return this.name.equals(name);
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
        return "Cookie{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
