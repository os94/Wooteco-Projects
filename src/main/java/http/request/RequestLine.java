package http.request;

import http.common.HttpMethod;
import http.exception.InvalidHeaderException;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

import static http.common.HeaderFields.*;
import static http.common.HttpMethod.GET;
import static http.common.HttpMethod.POST;

public class RequestLine {
    private static final Pattern HTTP_PATTERN = Pattern.compile("HTTP/.*");

    private final HttpMethod method;
    private String path;
    private final String queryString;
    private final String httpVersion;

    public RequestLine(String requestLine) {
        List<String> tokens = makeTokensFrom(requestLine);
        method = HttpMethod.valueOf(tokens.get(0));
        path = tokens.get(1);
        queryString = splitQueryString();
        httpVersion = tokens.get(2);
    }

    private String splitQueryString() {
        if (path.contains(QUESTION_MARK)) {
            path = path.split(REGEX_QUESTION_MARK)[0];
            return path.split(REGEX_QUESTION_MARK)[1];
        }
        return "";
    }

    private static List<String> makeTokensFrom(String requestLine) {
        List<String> tokens = Arrays.asList(requestLine.split(BLANK));
        validateRequestLine(requestLine, tokens);
        return tokens;
    }

    private static void validateRequestLine(String requestLine, List<String> tokens) {
        if (tokens.size() != 3 || !HttpMethod.matches(tokens.get(0)) || !HTTP_PATTERN.matcher(tokens.get(2)).matches()) {
            throw new InvalidHeaderException(requestLine + "은 유효하지않은 HttpRequest입니다.");
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

    public String getHttpVersion() {
        return httpVersion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestLine that = (RequestLine) o;
        return method == that.method &&
                Objects.equals(path, that.path) &&
                Objects.equals(queryString, that.queryString);
    }

    @Override
    public int hashCode() {
        return Objects.hash(method, path, queryString);
    }

    @Override
    public String toString() {
        return method + BLANK + path + queryString + BLANK + httpVersion + NEWLINE;
    }
}
