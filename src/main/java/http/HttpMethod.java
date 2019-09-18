package http;

import java.util.HashMap;
import java.util.Map;

public enum HttpMethod {
    GET, POST, PUT, DELETE;

    private static final Map<String, HttpMethod> map = new HashMap<>();

    static {
        for (HttpMethod httpMethod : HttpMethod.values()) {
            map.put(httpMethod.name(), httpMethod);
        }
    }

    public static boolean matches(String method) {
        return map.containsKey(method);
    }

    public boolean equals(String method) {
        return this.name().equals(method);
    }
}
