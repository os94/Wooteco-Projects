package controller.impl;

import controller.AbstractController;
import http.common.HttpStatus;
import http.request.HttpRequest;
import http.response.HttpResponse;
import view.ModelAndView;

public class HomeController extends AbstractController {
    public static final String URL = "/";

    @Override
    public ModelAndView doGet(HttpRequest request, HttpResponse response) {
        return new ModelAndView("/index.html", HttpStatus.FOUND);
    }
}
