package webserver.controller.impl;

import db.DataBase;
import http.request.HttpRequest;
import http.response.HttpResponse;
import webserver.controller.AbstractController;

import static http.common.HeaderFields.SET_COOKIE;

public class LoginController extends AbstractController {
    public static final String URL = "/user/login";

    @Override
    protected void doPost(HttpRequest request, HttpResponse response) {
        login(request, response);
    }

    private void login(HttpRequest request, HttpResponse response) {
        String userId = request.getParameter("userId");
        String password = request.getParameter("password");

        if (existUser(userId) && matchIdAndPassword(userId, password)) {
            response.addHeader(SET_COOKIE, "logined=true; Path=/");
            response.redirect("/index.html");
            return;
        }
        response.addHeader(SET_COOKIE, "logined=false;");
        response.redirect("/user/login_failed.html");
    }

    private boolean existUser(String userId) {
        return DataBase.findAll()
                .stream()
                .anyMatch(user -> user.matchId(userId));
    }

    private boolean matchIdAndPassword(String userId, String password) {
        return DataBase.findUserById(userId)
                .matchIdAndPassword(userId, password);
    }
}
