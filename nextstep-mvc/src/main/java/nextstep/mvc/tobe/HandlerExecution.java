package nextstep.mvc.tobe;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@FunctionalInterface
public interface HandlerExecution {
    ModelAndView handle(HttpServletRequest request, HttpServletResponse response);
}
