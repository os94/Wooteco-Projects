package http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class HttpResponse {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);

    private HttpStatus status;
    private Map<String, String> headerFields = new HashMap<>();
    private byte[] body;

    public HttpResponse(HttpStatus status) {
        this.status = status;
    }

    public HttpResponse(HttpStatus status, Map<String, String> headerFields, byte[] body) {
        this.status = status;
        this.headerFields = headerFields;
        this.body = body;
    }

    public void addHeader(String key, String value) {
        headerFields.put(key, value);
    }

//    public String forward() {
//        return convertHeader();
//    }

    public String forward() {
        return convertHeader() + new String(body);
    }

    public byte[] getBody() {
        return body;
    }

    public String sendRedirect(String location) {
        addHeader("Location", location);
        return convertHeader();
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

//    public void forward(int lengthOfBodyContent, String contentType, byte[] body) {
//        try {
//            responseHeader(lengthOfBodyContent, contentType);
//            responseBody(body);
//            dos.flush();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void forward(String contentType, String forwardingPath) throws IOException, URISyntaxException {
//        try {
//            body = FileIoUtils.loadFileFromClasspath(forwardingPath);
//
//            responseHeader(contentType);
//            responseBody();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void responseHeader(String contentType) {
//        dos.writeBytes("HTTP/1.1 200 OK \r\n");
//        dos.writeBytes("Content-Type: " + contentType + ";charset=utf-8\r\n");
//        dos.writeBytes("Content-Length: " + body.length + "\r\n");
//        dos.writeBytes("\r\n");
//    }
//
//    private void responseBody() throws IOException {
//        dos.write(body, 0, body.length);
//    }

}
