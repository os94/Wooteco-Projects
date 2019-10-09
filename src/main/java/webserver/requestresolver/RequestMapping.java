package webserver.requestresolver;

import http.common.HttpMethod;

import java.util.Objects;

public class RequestMapping {
    private final String url;
    private final HttpMethod method;

    public RequestMapping(String url, HttpMethod method) {
        this.method = method;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public HttpMethod getMethod() {
        return method;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestMapping that = (RequestMapping) o;
        return Objects.equals(url, that.url) &&
                method == that.method;
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, method);
    }

    @Override
    public String toString() {
        return "RequestMapping{" +
                "url='" + url + '\'' +
                ", method=" + method +
                '}';
    }
}
