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
import view.ViewResolver;

import java.util.HashMap;
import java.util.Map;

public class ControllerResolver implements Resolver {
    private static final Map<String, Controller> controllers = new HashMap<>();

    private final ViewResolver viewResolver = new ViewResolver();

    static {
        controllers.put(HomeController.URL, new HomeController());
        controllers.put(CreateUserController.URL, new CreateUserController());
        controllers.put(LoginController.URL, new LoginController());
        controllers.put(UserListController.URL, new UserListController());
    }

    @Override
    public void resolve(HttpRequest request, HttpResponse response) {
        ModelAndView modelAndView;
        String path = request.getPath();
        if (!controllers.containsKey(path)) {
            response.notFound(new IllegalArgumentException("URL Not Found"));
            return;
        }

        modelAndView = controllers.get(path).service(request, response);
        // TODO: 2019-09-30 if문 제거 ?!
        if (HttpStatus.OK == modelAndView.getStatus()) {
            String render = viewResolver.render(modelAndView);
            response.ok(render.getBytes());
            return;
        }
        if (HttpStatus.FOUND == modelAndView.getStatus()) {
            response.redirect(modelAndView.getViewName());
            return;
        }
    }
}
