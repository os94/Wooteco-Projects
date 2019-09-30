package http.common;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public enum HttpMethod {
    GET("doGet"),
    POST("doPost"),
    PUT("doPut"),
    DELETE("doDelete");

    private static final Map<HttpMethod, String> httpMethodNames = new EnumMap<>(HttpMethod.class);
    private static final Map<String, HttpMethod> controllerMethodNames = new HashMap<>();

    static {
        for (HttpMethod httpMethod : HttpMethod.values()) {
            httpMethodNames.put(httpMethod, httpMethod.name());
            controllerMethodNames.put(httpMethod.controllerMethodName, httpMethod);
        }
    }

    private String controllerMethodName;

    HttpMethod(String controllerMethodName) {
        this.controllerMethodName = controllerMethodName;
    }

    public static boolean matches(String httpMethodName) {
        return httpMethodNames.containsValue(httpMethodName);
    }

    public static Optional<HttpMethod> getMethodWhenMatchName(String controllerMethodName) {
        if (controllerMethodNames.containsKey(controllerMethodName)) {
            return Optional.of(controllerMethodNames.get(controllerMethodName));
        }
        return Optional.empty();
    }
}
