package http.request;

import http.common.HttpMethod;
import http.exception.InvalidRequestHeaderException;

import java.util.Arrays;
import java.util.List;

import static http.common.HttpMethod.GET;
import static http.common.HttpMethod.POST;

public class RequestLine {
    private static final String HTTP_VERSION = "HTTP/1.1";

    private HttpMethod method;
    private String path;
    private String queryString;

    public RequestLine(String requestLine) {
        List<String> tokens = makeTokensFrom(requestLine);
        method = HttpMethod.valueOf(tokens.get(0));
        path = tokens.get(1);
        queryString = splitQueryString();
    }

    private String splitQueryString() {
        if (path.contains("?")) {
            path = path.split("\\?")[0];
            return path.split("\\?")[1];
        }
        return "";
    }

    private static List<String> makeTokensFrom(String requestLine) {
        List<String> tokens = Arrays.asList(requestLine.split(" "));
        validateRequestLine(requestLine, tokens);
        return tokens;
    }

    private static void validateRequestLine(String requestLine, List<String> tokens) {
        if (tokens.size() != 3 || !HttpMethod.matches(tokens.get(0)) || !tokens.get(2).matches(HTTP_VERSION)) {
            throw new InvalidRequestHeaderException(requestLine);
        }
    }

    public boolean isGetMethod() {
        return GET.equals(method);
    }

    public boolean isPostMethod() {
        return POST.equals(method);
    }

    public String getPath() {
        return path;
    }

    public String getQueryString() {
        return queryString;
    }
}
