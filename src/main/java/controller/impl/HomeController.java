package controller.impl;

import controller.AbstractController;
import http.request.HttpRequest;
import http.response.HttpResponse;
import view.ModelAndView2;
import view.impl.RedirectView;

public class HomeController extends AbstractController {
    public static final String URL = "/";

    @Override
    public ModelAndView2 doGet(HttpRequest request, HttpResponse response) {
        return new ModelAndView2(new RedirectView("/index.html"));
    }
}
