package http.response;

import http.common.HttpStatus;

import java.util.Objects;

import static http.common.HeaderFields.BLANK;
import static http.common.HeaderFields.NEWLINE;

public class StatusLine {
    private final String httpVersion;
    private HttpStatus status;

    public StatusLine(String httpVersion) {
        this.httpVersion = httpVersion;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getHttpVersion() {
        return httpVersion;
    }

    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StatusLine that = (StatusLine) o;
        return Objects.equals(httpVersion, that.httpVersion) &&
                status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(httpVersion, status);
    }

    @Override
    public String toString() {
        return httpVersion + BLANK + status.getStatusCode() + BLANK + status.getStatusName() + NEWLINE;
    }
}
