package domain.controller.impl;

import domain.controller.AbstractController;
import domain.db.DataBase;
import http.request.HttpRequest;
import http.response.HttpResponse;
import webserver.view.ModelAndView;
import webserver.view.impl.HandlebarsView;
import webserver.view.impl.RedirectView;

import java.util.ArrayList;

public class UserListController extends AbstractController {
    public static final String URL = "/user/list";

    @Override
    public ModelAndView doGet(HttpRequest request, HttpResponse response) {
        return showUserList(request);
    }

    private ModelAndView showUserList(HttpRequest request) {
        ModelAndView modelAndView;
        if (isLogined(request)) {
            modelAndView = new ModelAndView(new HandlebarsView(URL));
            modelAndView.addObject("userList", new ArrayList<>(DataBase.findAll()));
            return modelAndView;
        }
        modelAndView = new ModelAndView(new RedirectView("/user/login.html"));
        return modelAndView;
    }

    private boolean isLogined(HttpRequest request) {
        return request.checkSessionAttribute("logined", true);
    }
}
