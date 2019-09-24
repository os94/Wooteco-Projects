package http.request;

import http.common.HeaderFields;
import org.apache.commons.lang3.StringUtils;
import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class HttpRequestFactory {
    public static HttpRequest createHttpRequest(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        String firstLine = br.readLine();

        RequestLine requestLine = new RequestLine(firstLine);
        HeaderFields headerFields = parseHeaderFields(br);
        Parameters requestBody = parseRequestData(br, headerFields.getContentLength());

        return new HttpRequest(requestLine, headerFields, requestBody);
    }

    private static HeaderFields parseHeaderFields(BufferedReader br) throws IOException {
        List<String> headerFields = new ArrayList<>();
        String line = br.readLine();
        headerFields.add(line);
        while (!StringUtils.isEmpty(line)) {
            headerFields.add(line);
            line = br.readLine();
        }
        return new HeaderFields(headerFields);
    }

    private static Parameters parseRequestData(BufferedReader br, int contentLength) throws IOException {
        String bodyData = "";
        if (contentLength != 0) {
            bodyData = IOUtils.readData(br, contentLength);
        }
        return new Parameters(bodyData);
    }
}
