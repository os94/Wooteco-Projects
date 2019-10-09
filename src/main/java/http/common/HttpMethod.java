package http.common;

import java.util.EnumMap;
import java.util.Map;

public enum HttpMethod {
    GET,
    POST,
    PUT,
    DELETE;

    private static final Map<HttpMethod, String> httpMethodNames = new EnumMap<>(HttpMethod.class);

    static {
        for (HttpMethod httpMethod : HttpMethod.values()) {
            httpMethodNames.put(httpMethod, httpMethod.name());
        }
    }

    public static boolean matchName(String httpMethodName) {
        return httpMethodNames.containsValue(httpMethodName);
    }
}
