package nextstep.mvc.tobe.handleradapter;

import nextstep.mvc.tobe.HandlerExecution;
import nextstep.mvc.tobe.view.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ResponseBodyHandlerAdapter implements HandlerAdapter {
    @Override
    public boolean supports(Object handler) {
        return ((HandlerExecution) handler).hasResponseBodyAnnotation();
    }

    @Override
    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object returnObject = ((HandlerExecution) handler).handle(request, response);
        // TODO: 2019-10-11 replace JsonView Logic; write response body data.
        return null;
    }
}
