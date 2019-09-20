package http.controller;

import http.HttpResponse;
import http.request.HttpRequest;

public interface Controller {
    void service(HttpRequest request, HttpResponse response);
}
