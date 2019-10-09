package controller;

import http.request.HttpRequest;
import http.response.HttpResponse;
import view.ModelAndView2;

public abstract class AbstractController {
    protected ModelAndView2 doGet(HttpRequest request, HttpResponse response) {
        throw new NotSupportedHttpMethodException(request.getMethod());
    }

    protected ModelAndView2 doPost(HttpRequest request, HttpResponse response) {
        throw new NotSupportedHttpMethodException(request.getMethod());
    }
}
