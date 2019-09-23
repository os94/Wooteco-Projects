package http.request;

import http.common.HeaderFields;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class HttpRequestFactory {
    private static final Logger logger = LoggerFactory.getLogger(HttpRequestFactory.class);

    public static HttpRequest createHttpRequest(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        String firstLine = br.readLine();

        RequestLine requestLine = new RequestLine(firstLine);
        HeaderFields headerFields = parseHeaderFields(br);
        RequestDatas datas = parseRequestData(br, requestLine, headerFields.getContentLength());

        return new HttpRequest(requestLine, headerFields, datas);
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

    private static RequestDatas parseRequestData(BufferedReader br, RequestLine requestLine, int contentLength) throws IOException {
        String bodyData = "";
        if (contentLength != 0) {
            bodyData = IOUtils.readData(br, contentLength);
        }
        return new RequestDatas(requestLine.getQueryString(), bodyData);
    }
}
