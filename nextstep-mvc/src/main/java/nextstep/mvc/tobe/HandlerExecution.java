package nextstep.mvc.tobe;

import nextstep.mvc.tobe.view.ModelAndView;
import nextstep.web.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Arrays;

public class HandlerExecution {
    private final Object declaredObject;
    private final Method method;

    public HandlerExecution(Object declaredObject, Method method) {
        this.declaredObject = declaredObject;
        this.method = method;
    }

    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return (ModelAndView) method.invoke(declaredObject, request, response);
    }

    public boolean hasResponseBodyAnnotation() {
        return method.getAnnotation(ResponseBody.class) != null;
    }
}
