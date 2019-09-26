package http.request;

import http.common.HeaderFields;
import http.common.HttpMethod;
import http.exception.HttpRequestCreateException;
import http.exception.InvalidHttpHeaderException;

import java.util.Objects;

import static http.common.HeaderFields.ACCEPT;
import static http.common.HeaderFields.REGEX_COMMA;

public class HttpRequest {
    public static final String COMMA = ".";

    private final RequestLine requestLine;
    private final HeaderFields headerFields;
    private final Parameters requestBody;

    public HttpRequest(RequestLine requestLine, HeaderFields headerFields, Parameters requestBody) {
        if (requestLine == null || headerFields == null || requestBody == null) {
            throw new HttpRequestCreateException("Http Request 생성에 실패했습니다.");
        }
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

    public boolean requestFile() {
        return getPath().contains(COMMA);
    }

    public boolean containHeader(String header) {
        return headerFields.contains(header);
    }

    public String getParameter(String parameter) {
        if (requestLine.containsParameter(parameter)) {
            return requestLine.getParameter(parameter);
        }
        if (requestBody.contains(parameter)) {
            return requestBody.getParameter(parameter);
        }
        throw new InvalidHttpHeaderException(parameter + "가 존재하지 않습니다.");
    }

    public String getContentTypeByAccept() {
        return getHeader(ACCEPT).split(REGEX_COMMA)[0];
    }

    public HttpMethod getMethod() {
        return requestLine.getMethod();
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
