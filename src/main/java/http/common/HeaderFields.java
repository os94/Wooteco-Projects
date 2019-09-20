package http.common;

import http.HttpResponse;
import http.exception.InvalidRequestHeaderException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class HeaderFields {
    private static final Logger logger = LoggerFactory.getLogger(HeaderFields.class);

    private final Map<String, String> headerFields;

    public HeaderFields(List<String> headerFields) {
        if (headerFields == null) {
            throw new InvalidRequestHeaderException("headerFields를 생성할 수 없습니다.");
        }
        this.headerFields = new HashMap<>();
        for (String headerField : headerFields) {
            String key = headerField.substring(0, headerField.indexOf(":"));
            String value = headerField.substring(headerField.indexOf(":") + 2);
            this.headerFields.put(key, value);
        }
    }

    public String getHeader(String fieldName) {
        if (headerFields.containsKey(fieldName)) {
            return headerFields.get(fieldName);
        }
        logger.error("Response Header에 " + fieldName + "이 존재하지않습니다.");
        throw new InvalidRequestHeaderException(fieldName + "를 찾을 수 없습니다.");
    }

    public int getContentLength() {
        return Integer.parseInt(headerFields.getOrDefault("Content-Length", String.valueOf(0)));
    }

    public void addHeader(String fieldName, String field) {
        headerFields.put(fieldName, field);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HeaderFields fields = (HeaderFields) o;
        return headerFields.equals(fields.headerFields);
    }

    @Override
    public int hashCode() {
        return Objects.hash(headerFields);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String field : headerFields.keySet()) {
            sb.append(field).append(": ").append(headerFields.get(field)).append("\r\n");
        }
        return sb.toString();
    }
}
