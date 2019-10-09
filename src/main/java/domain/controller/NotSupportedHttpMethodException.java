package domain.controller;

import http.common.HttpMethod;

public class NotSupportedHttpMethodException extends RuntimeException {
    public NotSupportedHttpMethodException(HttpMethod httpMethod) {
        super("Not Supported Http Method : " + httpMethod);
    }
}
