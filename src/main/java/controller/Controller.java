package controller;

import http.request.HttpRequest;
import http.response.HttpResponse;
import view.ModelAndView;

public interface Controller {
    ModelAndView service(HttpRequest request, HttpResponse response);
}
