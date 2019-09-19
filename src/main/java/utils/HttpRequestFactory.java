package utils;

import http.HttpMethod;
import http.HttpRequest;
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

    public static HttpRequest createHttpRequest(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        String requestLine = br.readLine();

        HttpMethod httpMethod = parseHttpMethod(requestLine);
        String path = parsePath(requestLine);
        Map<String, String> headerFields = getStringStringMap(br);
        Map<String, String> dataSet =
                parseDataSet(path, br, Integer.parseInt(headerFields.getOrDefault("Content-Length", String.valueOf(0))));

        return new HttpRequest(httpMethod, path, headerFields, dataSet);
    }

    public static HttpMethod parseHttpMethod(String requestLine) {
        return HttpMethod.valueOf(validateRequestLine(requestLine).get(0));
    }

    public static String parsePath(String requestLine) {
        return validateRequestLine(requestLine).get(1);
    }

    private static Map<String, String> getStringStringMap(BufferedReader br) throws IOException {
        Map<String, String> headerFields = new HashMap<>();
        String line = br.readLine();
        while (!"".equals(line)) {
            String key = line.substring(0, line.indexOf(":"));
            String value = line.substring(line.indexOf(":") + 2);
            headerFields.put(key, value);
            System.out.println("key:" + key + ", value: " + value);
            line = br.readLine();
        }
        return headerFields;
    }

    private static Map<String, String> parseDataSet(String path, BufferedReader br, int contentLength) throws IOException {
        Map<String, String> dataSet = new HashMap<>();
        String parameters = "";

        if (contentLength != 0) {
            parameters = IOUtils.readData(br, contentLength);
        }
        if (path.contains("\\?")) {
            parameters = path.substring(path.indexOf("\\?") + 1);
        }
        if (!"".equals(parameters)) {
            List<String> tokens = Arrays.asList(parameters.split("&"));
            tokens.stream()
                    .forEach(token -> dataSet.put(token.split("=")[0], token.split("=")[1]));
        }
        return dataSet;
    }

    private static List<String> validateRequestLine(String requestLine) {
        List<String> tokens = Arrays.asList(requestLine.split(" "));

        if (tokens.size() != 3 || !HttpMethod.matches(tokens.get(0)) || !tokens.get(2).matches(HTTP_VERSION)) {
            throw new InvalidRequestHeaderException(requestLine);
        }
        return tokens;
    }
}
