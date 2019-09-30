package http;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class HttpSession {
    private final String id;
    private final Map<String, Object> attributes = new HashMap<>();

    private HttpSession() {
        id = UUID.randomUUID().toString();
    }

    public static HttpSession create() {
        return new HttpSession();
    }

    public void setAttribute(String name, Object value) {
        attributes.put(name, value);
    }

    public String getId() {
        return id;
    }

    public Object getAttribute(String name) {
        return attributes.getOrDefault(name, null);
    }

    public void removeAttribute(String name) {
        attributes.remove(name);
    }

    public void invalidate() {
        attributes.clear();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HttpSession that = (HttpSession) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "HttpSession{" +
                "id='" + id + '\'' +
                ", attributes=" + attributes +
                '}';
    }
}
