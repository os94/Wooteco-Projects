package webserver.requestresolver;

import domain.controller.Controller;
import domain.controller.impl.CreateUserController;
import domain.controller.impl.HomeController;
import domain.controller.impl.LoginController;
import domain.controller.impl.UserListController;
import http.request.HttpRequest;
import http.response.HttpResponse;
import webserver.exception.UrlNotFoundException;
import webserver.view.ModelAndView;

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
    public ModelAndView resolve(HttpRequest request, HttpResponse response) {
        String path = request.getPath();
        if (!controllers.containsKey(path)) {
            throw new UrlNotFoundException(path);
        }
        return controllers.get(path).service(request, response);
    }
}
