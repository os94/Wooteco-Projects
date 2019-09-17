package utils;

import webserver.InvalidRequestHeaderException;

import java.util.Arrays;
import java.util.List;

public class RequestHeaderUtils {
    private static final String URI_PATTERN = "HTTP/1.1";

    public static String parseUrl(String header) {
        List<String> methods = Arrays.asList("GET", "POST", "PUT", "DELETE");
        List<String> words = Arrays.asList(header.split(" "));

        if (words.size() != 3 || !methods.contains(words.get(0)) || !words.get(2).matches(URI_PATTERN)) {
            throw new InvalidRequestHeaderException(header);
        }

        return words.get(1);
    }
}
