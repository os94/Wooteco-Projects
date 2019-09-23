package http.controller.impl;

import http.controller.AbstractController;
import http.request.HttpRequest;
import http.response.HttpResponse;

public class ResourcesController extends AbstractController {
    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
        response.forward(request.getPath());
    }
}
