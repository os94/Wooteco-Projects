package webserver.controller.impl;

import db.DataBase;
import http.HttpSession;
import http.request.HttpRequest;
import http.response.HttpResponse;
import webserver.SessionManager;
import webserver.controller.AbstractController;

import static http.common.HeaderFields.*;
import static webserver.SessionManager.JSESSIONID;

public class LoginController extends AbstractController {
    public static final String URL = "/user/login";

    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        login(request, response);
    }

    private void login(HttpRequest request, HttpResponse response) {
        String userId = request.getParameter("userId");
        String password = request.getParameter("password");

        if (existUser(userId) && matchIdAndPassword(userId, password)) {
            HttpSession session = SessionManager.createSession();
            session.setAttribute("logined", true);
            response.addHeader(SET_COOKIE, JSESSIONID + EQUAL + session.getId() + SEMI_COLON + BLANK + "path=/;");
            response.redirect("/index.html");
            return;
        }
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
