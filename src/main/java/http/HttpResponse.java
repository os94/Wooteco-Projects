package http;

import http.common.HeaderFields;
import http.common.HttpStatus;
import org.apache.tika.Tika;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class HttpResponse {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);

    private HttpStatus status;
    private HeaderFields headerFields;
    private byte[] body;

    public HttpResponse() {
        headerFields = new HeaderFields(new ArrayList<>());
    }

    public HttpStatus getStatus() {
        return status;
    }

    public byte[] getBody() {
        return body;
    }

    public String getHeader(String fieldName) {
        return headerFields.getHeader(fieldName);
    }

    public void addHeader(String fieldName, String field) {
        headerFields.addHeader(fieldName, field);
    }

    public void forward(String path) {
        try {
            status = HttpStatus.OK;
            body = FileIoUtils.loadFileFromClasspath(path);
            String type = new Tika().detect(path);

            headerFields.addHeader("Content-Type", type + ";charset=utf-8");
            headerFields.addHeader("Content-Length", String.valueOf(body.length));
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
            status = HttpStatus.NOT_FOUND;
            // todo: error page
        }
    }

    public void sendRedirect(String location) {
        status = HttpStatus.FOUND;
        headerFields.addHeader("Location", location);
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
        sb.append(headerFields);
        sb.append("\r\n");

        logger.debug("\n--response Header--\n{}", sb.toString());
        return sb.toString();
    }
}
