package webserver.resolver;

import http.controller.Controller;
import http.controller.impl.CreateUserController;
import http.controller.impl.HomeController;
import http.request.HttpRequest;
import http.response.HttpResponse;

import java.util.HashMap;
import java.util.Map;

public class ControllerResolver implements Resolver {
    private static final Map<String, Controller> controllers = new HashMap<>();

    static {
        controllers.put(HomeController.URL, new HomeController());
        controllers.put(CreateUserController.URL, new CreateUserController());
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
