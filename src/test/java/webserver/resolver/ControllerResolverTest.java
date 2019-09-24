package webserver.resolver;

import http.common.HttpStatus;
import http.request.HttpRequest;
import http.response.HttpResponse;
import org.junit.jupiter.api.Test;
import utils.HttpRequestFixtureUtils;

import static org.assertj.core.api.Assertions.assertThat;

class ControllerResolverTest {
    @Test
    void URL_Not_Found() {
        HttpRequest request = HttpRequestFixtureUtils.makeHttpRequestFixture("POST /user/foo HTTP/1.1");
        HttpResponse response = new HttpResponse(request);

        new ControllerResolver().resolve(request, response);

        assertThat(response.getStatus()).isEqualByComparingTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isEqualTo("URL Not Found".getBytes());
    }
}