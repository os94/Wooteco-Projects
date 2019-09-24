package http.common;

import http.exception.InvalidHttpHeaderException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class HeaderFields {
    private static final Logger logger = LoggerFactory.getLogger(HeaderFields.class);

    public static final String CONTENT_LENGTH = "Content-Length";
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String LOCATION = "Location";

    public static final String AMPERSAND = "&";
    public static final String EQUAL = "=";
    public static final String BLANK = " ";
    public static final String COLON = ":";
    public static final String NEWLINE = "\r\n";
    public static final String QUESTION_MARK = "?";
    public static final String REGEX_QUESTION_MARK = "\\?";

    private final Map<String, String> headerFields;

    public HeaderFields(List<String> headerFieldLines) {
        if (headerFieldLines == null) {
            throw new InvalidHttpHeaderException("headerFields를 생성할 수 없습니다.");
        }
        this.headerFields = new HashMap<>();
        setHeaderFields(headerFieldLines);
    }

    private void setHeaderFields(List<String> headerFieldLines) {
        for (String headerFieldLine : headerFieldLines) {
            String key = headerFieldLine.substring(0, headerFieldLine.indexOf(COLON));
            String value = headerFieldLine.substring(headerFieldLine.indexOf(COLON) + 2);
            this.headerFields.put(key, value);
        }
    }

    public String convert() {
        StringBuilder sb = new StringBuilder();
        for (String field : headerFields.keySet()) {
            sb.append(field).append(COLON).append(BLANK).append(headerFields.get(field)).append(NEWLINE);
        }
        return sb.toString();
    }

    public void addHeader(String fieldName, String field) {
        headerFields.put(fieldName, field);
    }

    public String getHeader(String fieldName) {
        if (headerFields.containsKey(fieldName)) {
            return headerFields.get(fieldName);
        }
        logger.error("Header에 " + fieldName + "이 존재하지않습니다.");
        throw new InvalidHttpHeaderException("Header에 " + fieldName + "이 존재하지않습니다.");
    }

    public int getContentLength() {
        return Integer.parseInt(headerFields.getOrDefault(CONTENT_LENGTH, String.valueOf(0)));
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
        return convert();
    }
}
