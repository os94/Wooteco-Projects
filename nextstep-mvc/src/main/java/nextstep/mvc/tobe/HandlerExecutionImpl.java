package nextstep.mvc.tobe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class HandlerExecutionImpl implements HandlerExecution {
    private static final Logger logger = LoggerFactory.getLogger(HandlerExecutionImpl.class);
    private static final Map<Method, HandlerExecutionImpl> handlerExecutionImpls = new HashMap<>();

    private final Object declaredObject;
    private final Method method;

    private HandlerExecutionImpl(Method method) {
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
        HandlerExecutionImpl handlerExecutionImpl = new HandlerExecutionImpl(method);
        handlerExecutionImpls.put(method, handlerExecutionImpl);
        return handlerExecutionImpl;
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

    public Object getDeclaredObject() {
        return declaredObject;
    }

    public Method getMethod() {
        return method;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HandlerExecutionImpl that = (HandlerExecutionImpl) o;
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
