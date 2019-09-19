package http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.IOUtils;
import webserver.InvalidRequestHeaderException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpRequestFactory {
    private static final String HTTP_VERSION = "HTTP/1.1";
    private static final Logger logger = LoggerFactory.getLogger(HttpRequestFactory.class);

    public static HttpRequest createHttpRequest(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        String requestLine = br.readLine();
        logger.debug("requestLine : {}", requestLine);

        HttpMethod httpMethod = parseHttpMethod(requestLine);
        String path = parsePath(requestLine);
        String pathWithoutParams = path.split("\\?")[0];
        Map<String, String> headerFields = parseHeaderFields(br);
        Map<String, String> dataSet =
                parseDataSet(path, br, Integer.parseInt(headerFields.getOrDefault("Content-Length", String.valueOf(0))));

        return new HttpRequest(httpMethod, pathWithoutParams, headerFields, dataSet);
    }

    private static HttpMethod parseHttpMethod(String requestLine) {
        return HttpMethod.valueOf(makeTokensFrom(requestLine).get(0));
    }

    private static String parsePath(String requestLine) {
        return makeTokensFrom(requestLine).get(1);
    }

    private static Map<String, String> parseHeaderFields(BufferedReader br) throws IOException {
        Map<String, String> headerFields = new HashMap<>();
        String line = br.readLine();
        while (!"".equals(line)) {
            logger.debug("headerField : {}", line);
            String key = line.substring(0, line.indexOf(":"));
            String value = line.substring(line.indexOf(":") + 2);
            headerFields.put(key, value);
            line = br.readLine();
        }
        return headerFields;
    }

    private static Map<String, String> parseDataSet(String path, BufferedReader br, int contentLength) throws IOException {
        Map<String, String> dataSet = new HashMap<>();
        String parameters = getData(path, br, contentLength);

        if (!"".equals(parameters)) {
            List<String> tokens = Arrays.asList(parameters.split("&"));
            tokens.forEach(token -> {
                logger.debug("data : {}", token);
                dataSet.put(token.split("=")[0], token.split("=")[1]);
            });
        }
        return dataSet;
    }

    private static String getData(String path, BufferedReader br, int contentLength) throws IOException {
        if (contentLength != 0) {
            return IOUtils.readData(br, contentLength);
        }
        if (path.contains("\\?")) {
            return path.substring(path.indexOf("\\?") + 1);
        }
        return "";
    }

    private static List<String> makeTokensFrom(String requestLine) {
        List<String> tokens = Arrays.asList(requestLine.split(" "));

        validateRequestLine(requestLine, tokens);
        return tokens;
    }

    private static void validateRequestLine(String requestLine, List<String> tokens) {
        if (tokens.size() != 3 || !HttpMethod.matches(tokens.get(0)) || !tokens.get(2).matches(HTTP_VERSION)) {
            throw new InvalidRequestHeaderException(requestLine);
        }
    }
}
