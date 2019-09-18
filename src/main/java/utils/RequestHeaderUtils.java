package utils;

import http.HttpMethod;
import webserver.InvalidRequestHeaderException;

import java.util.Arrays;
import java.util.List;

public class RequestHeaderUtils {
    private static final String HTTP_VERSION = "HTTP/1.1";

    public static String parseHttpMethod(String requestLine) {
        return validateRequestLine(requestLine).get(0);
    }

    public static String parsePath(String requestLine) {
        return validateRequestLine(requestLine).get(1);
    }

    private static List<String> validateRequestLine(String requestLine) {
        List<String> tokens = Arrays.asList(requestLine.split(" "));

        if (tokens.size() != 3 || !HttpMethod.matches(tokens.get(0)) || !tokens.get(2).matches(HTTP_VERSION)) {
            throw new InvalidRequestHeaderException(requestLine);
        }
        return tokens;
    }
}
