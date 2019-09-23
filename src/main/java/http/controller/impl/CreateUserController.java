package http.controller.impl;

import db.DataBase;
import http.controller.AbstractController;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.User;

import java.util.Map;

public class CreateUserController extends AbstractController {
    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        addUser(request, response);
    }

    private void addUser(HttpRequest request, HttpResponse response) {
        Map<String, String> userInfo = request.getDatas();
        DataBase.addUser(new User(userInfo.get("userId"), userInfo.get("password")
                , userInfo.get("name"), userInfo.get("email")));
        response.sendRedirect("/index.html");
    }
}
