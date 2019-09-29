package webserver.resolver;

import http.request.HttpRequest;
import http.response.HttpResponse;

public class RequestResolver {
    private final ResourceResolver resourceResolver = new ResourceResolver();
    private final ControllerResolver controllerResolver = new ControllerResolver();

    public void resolve(HttpRequest request, HttpResponse response) {
        if (request.requestFile()) {
            resourceResolver.resolve(request, response);
            return;
        }
        controllerResolver.resolve(request, response);
    }
}
