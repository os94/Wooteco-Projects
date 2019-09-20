package http;

import webserver.InvalidRequestHeaderException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HeaderFields {
    private Map<String, String> headerFields;

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
        throw new InvalidRequestHeaderException(fieldName + "를 찾을 수 없습니다.");
    }

    public int getContentLength() {
        return Integer.parseInt(headerFields.getOrDefault("Content-Length", String.valueOf(0)));
    }
}
