package nextstep.mvc.tobe;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface HandlerAdapter {
    boolean supports(Object handler);

    ModelAndView handle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception;
}
