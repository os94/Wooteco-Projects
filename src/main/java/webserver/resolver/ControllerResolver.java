package webserver.resolver;

import http.request.HttpRequest;
import http.response.HttpResponse;
import webserver.controller.Controller;
import webserver.controller.impl.CreateUserController;
import webserver.controller.impl.HomeController;
import webserver.controller.impl.LoginController;
import webserver.controller.impl.UserListController;

import java.util.HashMap;
import java.util.Map;

public class ControllerResolver implements Resolver {
    private static final Map<String, Controller> controllers = new HashMap<>();

    static {
        controllers.put(HomeController.URL, new HomeController());
        controllers.put(CreateUserController.URL, new CreateUserController());
        controllers.put(LoginController.URL, new LoginController());
        controllers.put(UserListController.URL, new UserListController());
    }

    @Override
    public void resolve(HttpRequest request, HttpResponse response) {
        String path = request.getPath();
        if (controllers.containsKey(path)) {
            controllers.get(path).service(request, response);
            return;
        }
        response.notFound(new IllegalArgumentException("URL Not Found"));
    }
}
