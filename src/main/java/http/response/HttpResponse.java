package http.response;

import http.common.HeaderFields;
import http.common.HttpStatus;
import org.apache.tika.Tika;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import static http.common.HeaderFields.*;

public class HttpResponse {
    private final StatusLine statusLine;
    private final HeaderFields headerFields;
    private byte[] body;

    public HttpResponse(String httpVersion) {
        statusLine = new StatusLine(httpVersion);
        headerFields = new HeaderFields(new ArrayList<>());
    }

    public void ok(byte[] body) {
        this.statusLine.setStatus(HttpStatus.OK);
        this.body = body;
        String type = new Tika().detect(body);
        this.headerFields.addHeader(CONTENT_TYPE, type + ";charset=utf-8");
        this.headerFields.addHeader(CONTENT_LENGTH, String.valueOf(body.length));
    }

    public void notFound(Exception e) {
        statusLine.setStatus(HttpStatus.NOT_FOUND);
        body = e.getMessage().getBytes();
    }

    public void redirect(String location) {
        statusLine.setStatus(HttpStatus.FOUND);
        headerFields.addHeader(LOCATION, location);
    }

    public String convert() {
        if (StringUtils.isEmpty(body)) {
            return convertHeader();
        }
        return convertHeader() + new String(body);
    }

    private String convertHeader() {
        return new StringBuilder()
                .append(statusLine)
                .append(headerFields.convert())
                .append(NEWLINE)
                .toString();
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
