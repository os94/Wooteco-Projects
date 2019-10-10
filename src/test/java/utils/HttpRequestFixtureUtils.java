package utils;

import http.request.HttpRequest;
import http.request.Parameters;
import http.request.RequestHeader;
import http.request.RequestLine;

import java.util.Arrays;
import java.util.List;

public class HttpRequestFixtureUtils {
    public static HttpRequest makeHttpRequestFixture(String requestLineString) {
        RequestLine requestLine = new RequestLine(requestLineString);
        Parameters requestBody = new Parameters("");
        RequestHeader headerFields = new RequestHeader(Arrays.asList(
                "Accept: */*"
        ));
        return new HttpRequest(requestLine, headerFields, requestBody);
    }

    public static HttpRequest makeHttpRequestFixture(String requestLineString, String requestBodyString) {
        RequestLine requestLine = new RequestLine(requestLineString);
        Parameters requestBody = new Parameters(requestBodyString);
        RequestHeader headerFields = new RequestHeader(Arrays.asList(
                "Accept: */*"
        ));
        return new HttpRequest(requestLine, headerFields, requestBody);
    }

    public static HttpRequest makeHttpRequestFixture(String requestLineString, List<String> headerFieldsString) {
        RequestLine requestLine = new RequestLine(requestLineString);
        Parameters requestBody = new Parameters("");
        RequestHeader headerFields = new RequestHeader(headerFieldsString);
        return new HttpRequest(requestLine, headerFields, requestBody);
    }
}
