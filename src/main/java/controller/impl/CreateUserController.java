package controller.impl;

import controller.AbstractController;
import db.DataBase;
import http.common.HttpStatus;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.User;
import view.ModelAndView;

public class CreateUserController extends AbstractController {
    public static final String URL = "/user/create";

    @Override
    public ModelAndView doPost(HttpRequest request, HttpResponse response) {
        return addUser(request);
    }

    private ModelAndView addUser(HttpRequest request) {
        String userId = request.getParameter("userId");
        String password = request.getParameter("password");
        String name = request.getParameter("name");
        String email = request.getParameter("email");

        DataBase.addUser(new User(userId, password, name, email));
        return new ModelAndView("/index.html", HttpStatus.FOUND);
    }
}
