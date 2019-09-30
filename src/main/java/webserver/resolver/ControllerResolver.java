package webserver.resolver;

import controller.Controller;
import controller.impl.CreateUserController;
import controller.impl.HomeController;
import controller.impl.LoginController;
import controller.impl.UserListController;
import http.common.HttpStatus;
import http.request.HttpRequest;
import http.response.HttpResponse;
import view.ModelAndView;

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
            return new ModelAndView("URL Not Found", HttpStatus.NOT_FOUND);
        }
        return controllers.get(path).service(request, response);
    }
}
