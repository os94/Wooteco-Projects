package utils;

import http.common.HeaderFields;
import http.request.HttpRequest;
import http.request.Parameters;
import http.request.RequestLine;

import java.util.Arrays;

public class HttpRequestFixtureUtils {
    public static HttpRequest makeHttpRequestFixture(String requestLineString) {
        RequestLine requestLine = new RequestLine(requestLineString);
        Parameters requestBody = new Parameters("userId=1&name=sean&password=1234&email=sean@gmail.com");
        HeaderFields headerFields = new HeaderFields(Arrays.asList(
                "Accept: */*"
        ));
        return new HttpRequest(requestLine, headerFields, requestBody);
    }
}
