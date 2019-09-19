package utils;

import http.HttpRequestFactory;
import org.junit.jupiter.api.Test;
import webserver.InvalidRequestHeaderException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class HttpRequestFactoryTest {
    private String testDirectory = "./src/test/resources/";
    @Test
    void invalid_header_method() throws FileNotFoundException {
        InputStream in = new FileInputStream(new File(testDirectory + "Invalid_Http_Header.txt"));
        assertThrows(InvalidRequestHeaderException.class, () -> {
            HttpRequestFactory.createHttpRequest(in);
        });
    }
//
//    @Test
//    void invalid_header_protocol() {
//        String header = "GET /index.html HHHH/1.1";
//        assertThrows(InvalidRequestHeaderException.class, () -> {
//            HttpRequestFactory.parsePath(header);
//        });
//    }
//
//    @Test
//    void invalid_header_split_length() {
//        String header = "GET /index.html hello HTTP/1.1";
//        assertThrows(InvalidRequestHeaderException.class, () -> {
//            HttpRequestFactory.parsePath(header);
//        });
//    }
//
//    @Test
//    void valid_header() {
//        String header = "GET /index.html HTTP/1.1";
//        assertThat(HttpRequestFactory.parsePath(header)).isEqualTo("/index.html");
//    }
}