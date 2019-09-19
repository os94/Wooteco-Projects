package http;

import webserver.InvalidRequestHeaderException;

import java.util.HashMap;
import java.util.Map;

import static http.HttpMethod.GET;
import static http.HttpMethod.POST;
import static java.util.Collections.unmodifiableMap;

public class HttpRequest {
    private HttpMethod method;
    private String path;
    private Map<String, String> headerFields = new HashMap<>();
    private Map<String, String> dataSet;

    public HttpRequest(HttpMethod method, String path, Map<String, String> headerFields, Map<String, String> dataSet) {
        this.method = method;
        this.path = path;
        this.headerFields = headerFields;
        this.dataSet = dataSet;
    }

    public boolean isGetMethod() {
        return GET.equals(method);
    }

    public boolean isPostMethod() {
        return POST.equals(method);
    }

    public String getHeader(String field) {
        if (headerFields.containsKey(field)) {
            return headerFields.get(field);
        }
        throw new InvalidRequestHeaderException(field + "를 찾을 수 없습니다.");
    }

    public String getData(String field) {
        if (dataSet.containsKey(field)) {
            return dataSet.get(field);
        }
        throw new InvalidRequestHeaderException(field + "를 찾을 수 없습니다.");
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public Map<String, String> getDataSet() {
        return unmodifiableMap(dataSet);
    }
}
