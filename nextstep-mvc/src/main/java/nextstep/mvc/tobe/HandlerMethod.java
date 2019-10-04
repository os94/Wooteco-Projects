package nextstep.mvc.tobe;

import nextstep.mvc.tobe.view.ModelAndView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class HandlerMethod implements HandlerExecution {
    private static final Logger logger = LoggerFactory.getLogger(HandlerMethod.class);
    private static final Map<Method, HandlerMethod> handlerExecutionImpls = new HashMap<>();

    private final Object declaredObject;
    private final Method method;

    private HandlerMethod(Method method) {
        try {
            this.declaredObject = method.getDeclaringClass().getConstructor().newInstance();
            this.method = method;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            logger.error("Error occurred while implement HandlerExecution", e);
            throw new RuntimeException(e);
        }
    }

    public static HandlerExecution of(Method method) {
        if (handlerExecutionImpls.containsKey(method)) {
            return handlerExecutionImpls.get(method);
        }
        HandlerMethod handlerMethod = new HandlerMethod(method);
        handlerExecutionImpls.put(method, handlerMethod);
        return handlerMethod;
    }

    @Override
    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response) {
        try {
            return (ModelAndView) method.invoke(declaredObject, request, response);
        } catch (IllegalAccessException | InvocationTargetException e) {
            logger.error("Error occurred while handle request", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HandlerMethod that = (HandlerMethod) o;
        return Objects.equals(declaredObject, that.declaredObject) &&
                Objects.equals(method, that.method);
    }

    @Override
    public int hashCode() {
        return Objects.hash(declaredObject, method);
    }

    @Override
    public String toString() {
        return "HandlerExecutionImpl{" +
                "declaredObject=" + declaredObject +
                ", method=" + method +
                '}';
    }
}
