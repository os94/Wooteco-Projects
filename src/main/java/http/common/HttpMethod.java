package http.common;

import java.util.HashMap;
import java.util.Map;

public enum HttpMethod {
    GET, POST, PUT, DELETE;

    private static final Map<String, HttpMethod> httpMethods = new HashMap<>();

    static {
        for (HttpMethod httpMethod : HttpMethod.values()) {
            httpMethods.put(httpMethod.name(), httpMethod);
        }
    }

    public static boolean matches(String method) {
        return httpMethods.containsKey(method);
    }
}
