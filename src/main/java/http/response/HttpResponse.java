package http.response;

import http.common.HttpStatus;
import http.request.HttpRequest;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Objects;

import static http.common.HeaderFields.*;
import static webserver.SessionManager.JSESSIONID;

public class HttpResponse {
    private final StatusLine statusLine;
    private final ResponseHeader responseHeader = new ResponseHeader();
    private byte[] body;
    private final HttpRequest request;

    public HttpResponse(HttpRequest request) {
        this.statusLine = new StatusLine(request.getHttpVersion());
        this.responseHeader.addHeader(CONTENT_TYPE, request.getContentTypeByAccept());
        this.request = request;
    }

    public void ok(byte[] body) {
        this.statusLine.setStatus(HttpStatus.OK);
        this.body = body;
        this.responseHeader.addHeader(CONTENT_LENGTH, String.valueOf(body.length));
    }

    public void redirect(String location) {
        statusLine.setStatus(HttpStatus.FOUND);
        responseHeader.addHeader(LOCATION, location);
    }

    public void notFound(String cause) {
        statusLine.setStatus(HttpStatus.NOT_FOUND);
        body = cause.getBytes();
        responseHeader.addHeader(CONTENT_LENGTH, String.valueOf(body.length));
    }

    public void internalServerError(String cause) {
        statusLine.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        body = cause.getBytes();
        responseHeader.addHeader(CONTENT_LENGTH, String.valueOf(body.length));
    }

    public String convert() {
        if (StringUtils.isEmpty(body)) {
            return convertHeader();
        }
        return convertHeader() + new String(body);
    }

    private String convertHeader() {
        if (request.containsCookie(JSESSIONID)) {
            responseHeader.addCookie(JSESSIONID, request.getCookie(JSESSIONID));
        }
        return statusLine +
                responseHeader.convert() +
                NEWLINE;
    }

    public HttpStatus getStatus() {
        return statusLine.getStatus();
    }

    public String getHttpVersion() {
        return statusLine.getHttpVersion();
    }

    public String getHeader(String fieldName) {
        return responseHeader.getHeader(fieldName);
    }

    public byte[] getBody() {
        return body;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HttpResponse response = (HttpResponse) o;
        return Objects.equals(statusLine, response.statusLine) &&
                Objects.equals(responseHeader, response.responseHeader) &&
                Arrays.equals(body, response.body) &&
                Objects.equals(request, response.request);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(statusLine, responseHeader, request);
        result = 31 * result + Arrays.hashCode(body);
        return result;
    }

    @Override
    public String toString() {
        return convertHeader();
    }
}
