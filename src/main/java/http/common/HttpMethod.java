package http.common;

import java.util.EnumMap;
import java.util.Map;

public enum HttpMethod {
    GET, POST, PUT, DELETE;

    private static final Map<HttpMethod, String> httpMethods = new EnumMap<>(HttpMethod.class);

    static {
        for (HttpMethod httpMethod : HttpMethod.values()) {
            httpMethods.put(httpMethod, httpMethod.name());
        }
    }

    public static boolean matches(String method) {
        return httpMethods.containsValue(method);
    }
}
