package domain.controller.impl;

import domain.controller.AbstractController;
import domain.db.DataBase;
import domain.model.User;
import http.request.HttpRequest;
import http.response.HttpResponse;
import webserver.view.ModelAndView;
import webserver.view.impl.RedirectView;

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
        return new ModelAndView(new RedirectView("/index.html"));
    }
}
