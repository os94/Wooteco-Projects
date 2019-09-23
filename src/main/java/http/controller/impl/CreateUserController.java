package http.controller.impl;

import db.DataBase;
import http.controller.AbstractController;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.User;

public class CreateUserController extends AbstractController {
    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        addUser(request, response);
    }

    private void addUser(HttpRequest request, HttpResponse response) {
        DataBase.addUser(new User(request.getParameter("userId"), request.getParameter("password")
                , request.getParameter("name"), request.getParameter("email")));
        response.sendRedirect("/index.html");
    }
}
