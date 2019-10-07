package nextstep.mvc.tobe;

import com.google.common.collect.Maps;
import nextstep.mvc.HandlerMapping;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;

public class AnnotationHandlerMapping implements HandlerMapping {
    private final ControllerScanner controllerScanner;
    private final Map<HandlerKey, HandlerExecution> handlerExecutions = Maps.newHashMap();

    public AnnotationHandlerMapping(Object... basePackage) {
        this.controllerScanner = new ControllerScanner(basePackage);
    }

    @Override
    public void initialize() {
        controllerScanner.getRequestMappingMethods().forEach(this::mapControllerMethod);
    }

    private void mapControllerMethod(Method method) {
        RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
        RequestMethod[] methods = requestMapping.method();
        if (requestMapping.method().length == 0) {
            methods = RequestMethod.values();
        }
        Arrays.stream(methods)
                .map(requestMethod -> new HandlerKey(requestMapping.value(), requestMethod))
                .forEach(key -> handlerExecutions.put(key, new HandlerExecution(controllerScanner.getInstanceFrom(method), method)));
    }

    @Override
    public HandlerExecution getHandler(HttpServletRequest request) {
        return handlerExecutions.get(new HandlerKey(request.getRequestURI(), RequestMethod.valueOf(request.getMethod())));
    }
}
