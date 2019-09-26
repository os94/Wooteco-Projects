package webserver.controller.impl;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import db.DataBase;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.controller.AbstractController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static http.common.HeaderFields.COOKIE;

public class UserListController extends AbstractController {
    private static final Logger logger = LoggerFactory.getLogger(UserListController.class);

    public static final String URL = "/user/list";

    @Override
    protected void doGet(HttpRequest request, HttpResponse response) {
        showUserList(request, response);
    }

    private void showUserList(HttpRequest request, HttpResponse response) {
        // TODO: 2019-09-26 리팩토링
        try {
            if (request.containHeader(COOKIE) && request.getHeader(COOKIE).contains("logined=true")) {
                response.ok(getUserListPage().getBytes());
                return;
            }
            response.redirect("/user/login.html");
        } catch (IOException e) {
            logger.error("Template Compile Error", e.getMessage());
            response.internalServerError(e);
        }
    }

    private String getUserListPage() throws IOException {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        Handlebars handlebars = new Handlebars(loader);
        Template template = handlebars.compile(URL);

        List<User> userList = new ArrayList<>(DataBase.findAll());
        return template.apply(userList);
    }
}
