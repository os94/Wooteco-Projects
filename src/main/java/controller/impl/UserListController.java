package controller.impl;

import controller.AbstractController;
import db.DataBase;
import http.common.HttpStatus;
import http.request.HttpRequest;
import http.response.HttpResponse;
import view.ModelAndView;

import java.util.ArrayList;

public class UserListController extends AbstractController {
    public static final String URL = "/user/list";

    @Override
    protected ModelAndView doGet(HttpRequest request, HttpResponse response) {
        return showUserList(request);
    }

    private ModelAndView showUserList(HttpRequest request) {
        ModelAndView modelAndView;
        if (isLogined(request)) {
            modelAndView = new ModelAndView(URL, HttpStatus.OK);
            modelAndView.addObject("userList", new ArrayList<>(DataBase.findAll()));
            return modelAndView;
        }
        modelAndView = new ModelAndView("/user/login.html", HttpStatus.FOUND);
        return modelAndView;
    }

    private boolean isLogined(HttpRequest request) {
        return request.checkSessionAttribute("logined", true);
    }
}
