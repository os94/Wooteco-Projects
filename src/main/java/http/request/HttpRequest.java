package http.request;

import http.common.HeaderFields;
import http.exception.InvalidHeaderException;

import java.util.Objects;

public class HttpRequest {
    private final RequestLine requestLine;
    private final HeaderFields headerFields;
    private final Parameters requestBody;

    public HttpRequest(RequestLine requestLine, HeaderFields headerFields, Parameters requestBody) {
        this.requestLine = requestLine;
        this.headerFields = headerFields;
        this.requestBody = requestBody;
    }

    public boolean isGetMethod() {
        return requestLine.isGetMethod();
    }

    public boolean isPostMethod() {
        return requestLine.isPostMethod();
    }

    public String getParameter(String parameter) {
        if (requestLine.containsParameter(parameter)) {
            return requestLine.getParameter(parameter);
        }
        if (requestBody.contains(parameter)) {
            return requestBody.getParameter(parameter);
        }
        throw new InvalidHeaderException(parameter + "가 존재하지 않습니다.");
    }

    public String getHttpVersion() {
        return requestLine.getHttpVersion();
    }

    public String getHeader(String fieldName) {
        return headerFields.getHeader(fieldName);
    }

    public String getPath() {
        return requestLine.getPath();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HttpRequest that = (HttpRequest) o;
        return Objects.equals(requestLine, that.requestLine) &&
                Objects.equals(headerFields, that.headerFields) &&
                Objects.equals(requestBody, that.requestBody);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requestLine, headerFields, requestBody);
    }

    @Override
    public String toString() {
        return requestLine.toString() + headerFields + requestBody;
    }
}
