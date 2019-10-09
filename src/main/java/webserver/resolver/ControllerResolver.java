package webserver.resolver;

import controller.impl.CreateUserController;
import controller.impl.HomeController;
import controller.impl.LoginController;
import controller.impl.UserListController;
import http.common.HttpMethod;
import http.request.HttpRequest;
import http.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import view.ModelAndView2;
import webserver.exception.UrlNotFoundException;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ControllerResolver implements Resolver {
    private static final Logger logger = LoggerFactory.getLogger(ControllerResolver.class);

    private static final Map<RequestMapping, Method> controllers = new HashMap<>();

    static {
        registerControllerMethods(HomeController.class, HomeController.URL);
        registerControllerMethods(CreateUserController.class, CreateUserController.URL);
        registerControllerMethods(LoginController.class, LoginController.URL);
        registerControllerMethods(UserListController.class, UserListController.URL);
    }

    private static void registerControllerMethods(Class className, String url) {
        Method[] methods = className.getDeclaredMethods();
        Arrays.stream(methods)
                .forEach(method -> registerIfMatch(method, url));
    }

    private static void registerIfMatch(Method method, String url) {
        Optional<HttpMethod> maybeHttpMethod = HttpMethod.getMethodWhenMatchName(method.getName());
        maybeHttpMethod.ifPresent(httpMethod -> controllers.put(new RequestMapping(url, httpMethod), method));
    }

    @Override
    public ModelAndView2 resolve2(HttpRequest request, HttpResponse response) throws Exception {
        RequestMapping requestMapping = request.getRequestMapping();

        if (!controllers.containsKey(requestMapping)) {
            throw new UrlNotFoundException(requestMapping.toString());
        }
        Method controllerMethod = controllers.get(requestMapping);
        Object controllerInstance = controllerMethod.getDeclaringClass().getDeclaredConstructor().newInstance();
        return (ModelAndView2) controllerMethod.invoke(controllerInstance, request, response);
    }
}
