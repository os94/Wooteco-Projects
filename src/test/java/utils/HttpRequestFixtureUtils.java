package utils;

import http.common.HeaderFields;
import http.request.HttpRequest;
import http.request.Parameters;
import http.request.RequestLine;

import java.util.Arrays;
import java.util.List;

public class HttpRequestFixtureUtils {
    public static HttpRequest makeHttpRequestFixture(String requestLineString) {
        RequestLine requestLine = new RequestLine(requestLineString);
        Parameters requestBody = new Parameters("");
        HeaderFields headerFields = new HeaderFields(Arrays.asList(
                "Accept: */*"
        ));
        return new HttpRequest(requestLine, headerFields, requestBody);
    }

    public static HttpRequest makeHttpRequestFixture(String requestLineString, String requestBodyString) {
        RequestLine requestLine = new RequestLine(requestLineString);
        Parameters requestBody = new Parameters(requestBodyString);
        HeaderFields headerFields = new HeaderFields(Arrays.asList(
                "Accept: */*"
        ));
        return new HttpRequest(requestLine, headerFields, requestBody);
    }

    public static HttpRequest makeHttpRequestFixture(String requestLineString, List<String> headerFieldsString) {
        RequestLine requestLine = new RequestLine(requestLineString);
        Parameters requestBody = new Parameters("");
        HeaderFields headerFields = new HeaderFields(headerFieldsString);
        return new HttpRequest(requestLine, headerFields, requestBody);
    }
}
