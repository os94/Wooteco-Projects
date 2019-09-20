package http;

import org.apache.tika.Tika;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.LinkedHashMap;
import java.util.Map;

public class HttpResponseFactory {

    public static HttpResponse createHttpResponse(HttpStatus status, String path) throws IOException, URISyntaxException {
        byte[] body = FileIoUtils.loadFileFromClasspath(path);
        String type = new Tika().detect(path);
        Map<String, String> headerFields = new LinkedHashMap<>();

        headerFields.put("Content-Type", type + ";charset=utf-8");
        headerFields.put("Content-Length", String.valueOf(body.length));


        return new HttpResponse(status, headerFields, body);
    }
}
