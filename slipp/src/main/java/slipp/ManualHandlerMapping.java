package slipp;

import nextstep.mvc.DispatcherServlet;
import nextstep.mvc.HandlerMapping;
import nextstep.mvc.asis.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class ManualHandlerMapping implements HandlerMapping {
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);
    private Map<String, Controller> mappings = new HashMap<>();

    @Override
    public void initialize() {
        //mappings.put("/", new HomeController());
        //mappings.put("/users/form", new ForwardController("/user/form.jsp"));
        //mappings.put("/users/loginForm", new ForwardController("/user/login.jsp"));
        //mappings.put("/users", new ListUserController());
        //mappings.put("/users/login", new LoginController());
        //mappings.put("/users/profile", new ProfileController());
        //mappings.put("/users/logout", new LogoutController());
        //mappings.put("/users/create", new CreateUserController());
        //mappings.put("/users/updateForm", new UpdateFormUserController());
        //mappings.put("/users/update", new UpdateUserController());

        logger.info("Initialized Request Mapping!");
        mappings.keySet().forEach(path -> {
            logger.info("Path : {}, Controller : {}", path, mappings.get(path).getClass());
        });
    }

    @Override
    public Controller getHandler(HttpServletRequest request) {
        return mappings.get(request.getRequestURI());
    }

    void put(String url, Controller controller) {
        mappings.put(url, controller);
    }
}
