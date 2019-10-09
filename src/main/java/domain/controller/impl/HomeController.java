package domain.controller.impl;

import domain.controller.AbstractController;
import http.request.HttpRequest;
import http.response.HttpResponse;
import webserver.view.ModelAndView;
import webserver.view.impl.RedirectView;

public class HomeController extends AbstractController {
    public static final String URL = "/";

    @Override
    public ModelAndView doGet(HttpRequest request, HttpResponse response) {
        return new ModelAndView(new RedirectView("/index.html"));
    }
}
