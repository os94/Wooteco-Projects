package http.request;

import http.common.HeaderFields;

import java.util.Map;
import java.util.Objects;

public class HttpRequest {
    private final RequestLine requestLine;
    private final HeaderFields headerFields;
    private final RequestDatas datas;

    public HttpRequest(RequestLine requestLine, HeaderFields headerFields, RequestDatas datas) {
        this.requestLine = requestLine;
        this.headerFields = headerFields;
        this.datas = datas;
    }

    public boolean isGetMethod() {
        return requestLine.isGetMethod();
    }

    public boolean isPostMethod() {
        return requestLine.isPostMethod();
    }

    public String getHttpVersion() {
        return requestLine.getHttpVersion();
    }

    public String getHeader(String fieldName) {
        return headerFields.getHeader(fieldName);
    }

    public String getData(String fieldName) {
        return datas.getData(fieldName);
    }

    public String getPath() {
        return requestLine.getPath();
    }

    public Map<String, String> getDatas() {
        return datas.getDatas();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HttpRequest that = (HttpRequest) o;
        return Objects.equals(requestLine, that.requestLine) &&
                Objects.equals(headerFields, that.headerFields) &&
                Objects.equals(datas, that.datas);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requestLine, headerFields, datas);
    }

    @Override
    public String toString() {
        return requestLine.toString() + headerFields + datas;
    }
}
