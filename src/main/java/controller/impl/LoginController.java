package controller.impl;

import controller.AbstractController;
import db.DataBase;
import http.HttpSession;
import http.common.HttpStatus;
import http.request.HttpRequest;
import http.response.HttpResponse;
import view.ModelAndView;
import webserver.SessionManager;

import static http.common.HeaderFields.*;
import static webserver.SessionManager.JSESSIONID;

public class LoginController extends AbstractController {
    public static final String URL = "/user/login";

    @Override
    public ModelAndView doPost(HttpRequest request, HttpResponse response) {
        return login(request, response);
    }

    private ModelAndView login(HttpRequest request, HttpResponse response) {
        String userId = request.getParameter("userId");
        String password = request.getParameter("password");

        if (existUser(userId) && matchIdAndPassword(userId, password)) {
            HttpSession session = SessionManager.getInstance().createSession();
            session.setAttribute("logined", true);
            response.addHeader(SET_COOKIE, JSESSIONID + EQUAL + session.getId() + SEMI_COLON + BLANK + "path=/;");
            return new ModelAndView("/index.html", HttpStatus.FOUND);
        }
        return new ModelAndView("/user/login_failed.html", HttpStatus.FOUND);
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
