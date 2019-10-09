package controller.impl;

import controller.AbstractController;
import db.DataBase;
import http.request.HttpRequest;
import http.response.HttpResponse;
import view.ModelAndView2;
import view.impl.HandlebarsView;
import view.impl.RedirectView;

import java.util.ArrayList;

public class UserListController extends AbstractController {
    public static final String URL = "/user/list";

    public ModelAndView2 doGet(HttpRequest request, HttpResponse response) {
        return showUserList(request);
    }

    private ModelAndView2 showUserList(HttpRequest request) {
        ModelAndView2 modelAndView2;
        if (isLogined(request)) {
            modelAndView2 = new ModelAndView2(new HandlebarsView(URL));
            modelAndView2.addObject("userList", new ArrayList<>(DataBase.findAll()));
            return modelAndView2;
        }
        modelAndView2 = new ModelAndView2(new RedirectView("/user/login.html"));
        return modelAndView2;
    }

    private boolean isLogined(HttpRequest request) {
        return request.checkSessionAttribute("logined", true);
    }
}
