package webserver.resolver;

import http.request.HttpRequest;
import http.response.HttpResponse;

public class RequestMapping {
    private ResourceResolver resourceResolver = new ResourceResolver();
    private ControllerResolver controllerResolver = new ControllerResolver();

    public void map(HttpRequest request, HttpResponse response) {
        if (request.requestFile()) {
            resourceResolver.resolve(request, response);
            return;
        }
        controllerResolver.resolve(request, response);
    }
}
