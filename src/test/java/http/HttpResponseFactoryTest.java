package http;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;

class HttpResponseFactoryTest {

    @Test
    void typeTest() throws IOException, URISyntaxException {
        HttpResponse response = HttpResponseFactory.createHttpResponse(HttpStatus.OK, "./templates/index.html");
    }

    @Test
    void typeTest2() throws IOException, URISyntaxException {
        HttpResponse response = HttpResponseFactory.createHttpResponse(HttpStatus.OK, "./static/css/style.css");
    }
}