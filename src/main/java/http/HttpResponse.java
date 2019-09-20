package http;

import org.apache.tika.Tika;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class HttpResponse {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);

    private HttpStatus status;
    private Map<String, String> headerFields = new HashMap<>();
    private byte[] body;

    public HttpResponse() {
    }

    public HttpStatus getStatus() {
        return status;
    }

    public Map<String, String> getHeaderFields() {
        return headerFields;
    }

    public byte[] getBody() {
        return body;
    }

    public String getHeader(String fieldName) {
        if (headerFields.containsKey(fieldName)) {
            return headerFields.get(fieldName);
        }
        logger.error("Response Header에 " + fieldName + "이 존재하지않습니다.");
        throw new InvalidResponseException("Response Header에 " + fieldName + "이 존재하지않습니다.");
    }

    public void addHeader(String fieldName, String field) {
        headerFields.put(fieldName, field);
    }

    public void forward(String path) {
        try {
            status = HttpStatus.OK;
            body = FileIoUtils.loadFileFromClasspath(path);
            String type = new Tika().detect(path);

            headerFields.put("Content-Type", type + ";charset=utf-8");
            headerFields.put("Content-Length", String.valueOf(body.length));
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
            status = HttpStatus.NOT_FOUND;
            // todo: error page
        }
    }

    public void sendRedirect(String location) {
        status = HttpStatus.FOUND;
        addHeader("Location", location);
    }

    public String convert() {
        if (StringUtils.isEmpty(body)) {
            return convertHeader();
        }
        return convertHeader() + new String(body);
    }

    private String convertHeader() {
        StringBuilder sb = new StringBuilder();
        sb.append("HTTP/1.1 ").append(status.getStatusCode()).append(" ").append(status.getStatusName()).append("\r\n");
        for (String field : headerFields.keySet()) {
            sb.append(field).append(": ").append(headerFields.get(field)).append("\r\n");
        }
        sb.append("\r\n");
        logger.debug("\n--response Header--\n{}", sb.toString());
        return sb.toString();
    }
}
