package http.controller;

import http.controller.impl.CreateUserController;
import http.controller.impl.ResourcesController;
import org.apache.commons.collections4.map.HashedMap;

import java.util.Map;

public class RequestMapping {
    private static final Map<String, Controller> controllers = new HashedMap<>();

    static {
        controllers.put("/user/create", new CreateUserController());
    }

    public static Controller getController(String path) {
        return controllers.getOrDefault(path, new ResourcesController());
    }
}
