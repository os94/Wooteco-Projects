package domain.controller.impl;

import domain.controller.AbstractController;
import domain.db.DataBase;
import http.common.HttpSession;
import http.request.HttpRequest;
import http.response.HttpResponse;
import webserver.view.ModelAndView;
import webserver.view.impl.RedirectView;

public class LoginController extends AbstractController {
    public static final String URL = "/user/login";

    public ModelAndView doPost(HttpRequest request, HttpResponse response) {
        return login(request);
    }

    private ModelAndView login(HttpRequest request) {
        String userId = request.getParameter("userId");
        String password = request.getParameter("password");

        if (existUser(userId) && matchIdAndPassword(userId, password)) {
            HttpSession session = request.getSession(true);
            session.setAttribute("logined", true);
            return new ModelAndView(new RedirectView("/index.html"));
        }
        return new ModelAndView(new RedirectView("/user/login_failed.html"));
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
