package http;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class HttpResponseFactoryTest {

    @Test
    void get_html_file() throws IOException, URISyntaxException {
        HttpResponse response = HttpResponseFactory.createHttpResponse(HttpStatus.OK, "./templates/index.html");
    }

    @Test
    void get_static_file() throws IOException, URISyntaxException {
        HttpResponse response = HttpResponseFactory.createHttpResponse(HttpStatus.OK, "./static/css/styles.css");
    }
}