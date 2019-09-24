package webserver.resolver;

import http.request.HttpRequest;
import http.response.HttpResponse;

public interface Resolver {
    void resolve(HttpRequest request, HttpResponse response);
}
