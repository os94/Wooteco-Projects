package nextstep.mvc.tobe;

import nextstep.mvc.tobe.view.ModelAndView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

public class HandlerMethod implements HandlerExecution {
    private static final Logger logger = LoggerFactory.getLogger(HandlerMethod.class);

    private final Object declaredObject;
    private final Method method;

    public HandlerMethod(Method method) {
        try {
            this.declaredObject = method.getDeclaringClass().getConstructor().newInstance();
            this.method = method;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            logger.error("Error occurred while implement HandlerExecution", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return (ModelAndView) method.invoke(declaredObject, request, response);
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
