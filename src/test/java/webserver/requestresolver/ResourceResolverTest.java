package webserver.requestresolver;

import http.request.HttpRequest;
import http.response.HttpResponse;
import org.junit.jupiter.api.Test;
import utils.HttpRequestFixtureUtils;
import webserver.exception.ResourceNotFoundException;
import webserver.view.ModelAndView;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ResourceResolverTest {
    @Test
    void URL_Not_Found() {
        HttpRequest request = HttpRequestFixtureUtils.makeHttpRequestFixture("GET /foo.xyz HTTP/1.1");
        HttpResponse response = new HttpResponse(request);

        assertThrows(ResourceNotFoundException.class, () -> new ResourceResolver().resolve(request, response));
    }

    @Test
    void success_to_find_file() {
        HttpRequest request = HttpRequestFixtureUtils.makeHttpRequestFixture("GET /index.html HTTP/1.1");
        HttpResponse response = new HttpResponse(request);

        assertThat(new ResourceResolver().resolve(request, response)).isInstanceOf(ModelAndView.class);
    }
}