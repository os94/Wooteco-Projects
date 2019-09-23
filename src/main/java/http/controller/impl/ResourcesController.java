package http.controller.impl;

import http.HttpResponse;
import http.controller.AbstractController;
import http.request.HttpRequest;

public class ResourcesController extends AbstractController {
    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
        response.forward(request.getPath());
    }
}
