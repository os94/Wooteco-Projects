package nextstep.mvc.tobe;

import com.google.common.collect.Maps;
import nextstep.mvc.HandlerMapping;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;

public class AnnotationHandlerMapping implements HandlerMapping {

    private static final Logger logger = LoggerFactory.getLogger(AnnotationHandlerMapping.class);

    private final ControllerScanner controllerScanner;
    private final Map<HandlerKey, HandlerExecution> handlerExecutions = Maps.newHashMap();

    public AnnotationHandlerMapping(Object... basePackage) {
        this.controllerScanner = new ControllerScanner(basePackage);
    }

    @Override
    public void initialize() {
        controllerScanner.getRequestMappingMethods().forEach(this::mapControllerMethod);

        logger.info("Initialized Request Mapping!");
        handlerExecutions.keySet().forEach(handlerKey -> logger.info(handlerKey.toString()));
    }

    private void mapControllerMethod(Method method) {
        RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
        RequestMethod[] methods = getMethodsWith(requestMapping);
        Arrays.stream(methods)
                .map(requestMethod -> new HandlerKey(requestMapping.value(), requestMethod))
                .forEach(key -> handlerExecutions.put(key, new HandlerExecution(controllerScanner.getInstanceFrom(method), method)));
    }

    private RequestMethod[] getMethodsWith(RequestMapping requestMapping) {
        RequestMethod[] methods = requestMapping.method();
        if (requestMapping.method().length == 0) {
            methods = RequestMethod.values();
        }
        return methods;
    }

    @Override
    public HandlerExecution getHandler(HttpServletRequest request) {
        return handlerExecutions.get(new HandlerKey(request.getRequestURI(), RequestMethod.valueOf(request.getMethod())));
    }
}
