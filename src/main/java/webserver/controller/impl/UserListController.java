package webserver.controller.impl;

import http.request.HttpRequest;
import http.response.HttpResponse;
import webserver.controller.AbstractController;

import static http.common.HeaderFields.COOKIE;

public class UserListController extends AbstractController {
    public static final String URL = "user/list";

    @Override
    protected void doGet(HttpRequest request, HttpResponse response) {
        if (request.getHeader(COOKIE).contains("logined=true")) {
            response.ok("123".getBytes());
            return;
        }
        response.redirect("/user/login.html");
    }
}
