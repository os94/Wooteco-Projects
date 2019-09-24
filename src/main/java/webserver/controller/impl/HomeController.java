package webserver.controller.impl;

import webserver.controller.AbstractController;
import http.request.HttpRequest;
import http.response.HttpResponse;

public class HomeController extends AbstractController {
    public static final String URL = "/";

    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
        response.redirect("/index.html");
    }
}
