package controller.impl;

import controller.AbstractController;
import db.DataBase;
import http.common.HttpSession;
import http.common.HttpStatus;
import http.request.HttpRequest;
import http.response.HttpResponse;
import view.ModelAndView;

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
            HttpSession session = request.getSession(true);
            session.setAttribute("logined", true);
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
