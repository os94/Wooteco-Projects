package webserver.requestresolver;

import http.request.HttpRequest;
import http.response.HttpResponse;
import org.junit.jupiter.api.Test;
import utils.HttpRequestFixtureUtils;
import webserver.exception.UrlNotFoundException;
import webserver.view.ModelAndView;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ControllerResolverTest {
    @Test
    void URL_Not_Found() {
        HttpRequest request = HttpRequestFixtureUtils.makeHttpRequestFixture("POST /user/foo HTTP/1.1");
        HttpResponse response = new HttpResponse(request);

        assertThrows(UrlNotFoundException.class, () -> new ControllerResolver().resolve(request, response));
    }

    @Test
    void success_to_find_controller() {
        HttpRequest request = HttpRequestFixtureUtils.makeHttpRequestFixture("GET / HTTP/1.1");
        HttpResponse response = new HttpResponse(request);

        assertThat(new ControllerResolver().resolve(request, response)).isInstanceOf(ModelAndView.class);
    }
}