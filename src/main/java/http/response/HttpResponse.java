package http.response;

import http.common.HeaderFields;
import http.common.HttpStatus;
import http.request.HttpRequest;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import static http.common.HeaderFields.*;

public class HttpResponse {
    private final StatusLine statusLine;
    private final HeaderFields headerFields = new HeaderFields(new ArrayList<>());
    private byte[] body;

    public HttpResponse(HttpRequest request) {
        statusLine = new StatusLine(request.getHttpVersion());
        headerFields.addHeader(CONTENT_TYPE, request.getContentTypeByAccept());
    }

    public void ok(byte[] body) {
        this.statusLine.setStatus(HttpStatus.OK);
        this.body = body;
        this.headerFields.addHeader(CONTENT_LENGTH, String.valueOf(body.length));
    }

    public void redirect(String location) {
        statusLine.setStatus(HttpStatus.FOUND);
        headerFields.addHeader(LOCATION, location);
    }

    public void notFound(String cause) {
        statusLine.setStatus(HttpStatus.NOT_FOUND);
        body = cause.getBytes();
        headerFields.addHeader(CONTENT_LENGTH, String.valueOf(body.length));
    }

    public void internalServerError(String cause) {
        statusLine.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        body = cause.getBytes();
        headerFields.addHeader(CONTENT_LENGTH, String.valueOf(body.length));
    }

    public String convert() {
        if (StringUtils.isEmpty(body)) {
            return convertHeader();
        }
        return convertHeader() + new String(body);
    }

    private String convertHeader() {
        return statusLine +
                headerFields.convert() +
                NEWLINE;
    }

    public void addHeader(String fieldName, String field) {
        headerFields.addHeader(fieldName, field);
    }

    public HttpStatus getStatus() {
        return statusLine.getStatus();
    }

    public String getHttpVersion() {
        return statusLine.getHttpVersion();
    }

    public String getHeader(String fieldName) {
        return headerFields.getHeader(fieldName);
    }

    public byte[] getBody() {
        return body;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HttpResponse that = (HttpResponse) o;
        return Objects.equals(statusLine, that.statusLine) &&
                Objects.equals(headerFields, that.headerFields) &&
                Arrays.equals(body, that.body);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(statusLine, headerFields);
        result = 31 * result + Arrays.hashCode(body);
        return result;
    }

    @Override
    public String toString() {
        return convertHeader();
    }
}
