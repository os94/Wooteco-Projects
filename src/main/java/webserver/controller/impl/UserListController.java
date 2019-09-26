package webserver.controller.impl;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import db.DataBase;
import http.HttpSession;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.controller.AbstractController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static http.common.HeaderFields.BLANK;
import static http.common.HeaderFields.COOKIE;
import static webserver.SessionManager.JSESSIONID;

public class UserListController extends AbstractController {
    private static final Logger logger = LoggerFactory.getLogger(UserListController.class);

    public static final String URL = "/user/list";

    @Override
    protected void doGet(HttpRequest request, HttpResponse response) {
        showUserList(request, response);
    }

    private void showUserList(HttpRequest request, HttpResponse response) {
        try {
            if (isLogined(request)) {
                response.ok(getUserListPage().getBytes());
                return;
            }
            response.redirect("/user/login.html");
        } catch (IOException e) {
            logger.error("Template Compile Error", e.getMessage());
            response.internalServerError(e);
        }
    }

    private boolean isLogined(HttpRequest request) {
        if (!request.containHeader(COOKIE) || notContainJSESSIONIDinCookie(request)) {
            return false;
        }
        HttpSession session = request.getSession();
        return session.getAttribute("logined").equals(true);
    }

    private boolean notContainJSESSIONIDinCookie(HttpRequest request) {
        return BLANK.equals(request.getCookie(JSESSIONID));
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
