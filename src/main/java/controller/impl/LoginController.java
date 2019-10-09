package controller.impl;

import controller.AbstractController;
import db.DataBase;
import http.common.HttpSession;
import http.request.HttpRequest;
import http.response.HttpResponse;
import view.ModelAndView2;
import view.impl.RedirectView;

public class LoginController extends AbstractController {
    public static final String URL = "/user/login";

    public ModelAndView2 doPost(HttpRequest request, HttpResponse response) {
        return login(request, response);
    }

    private ModelAndView2 login(HttpRequest request, HttpResponse response) {
        String userId = request.getParameter("userId");
        String password = request.getParameter("password");

        if (existUser(userId) && matchIdAndPassword(userId, password)) {
            HttpSession session = request.getSession(true);
            session.setAttribute("logined", true);
            return new ModelAndView2(new RedirectView("/index.html"));
        }
        return new ModelAndView2(new RedirectView("/user/login_failed.html"));
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
