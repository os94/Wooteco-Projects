package nextstep.mvc.tobe.handleradapter;

import nextstep.mvc.tobe.HandlerExecution;
import nextstep.mvc.tobe.view.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AnnotationHandlerAdapter implements HandlerAdapter {
    @Override
    public boolean supports(Object handler) {
        return (handler instanceof HandlerExecution) && !((HandlerExecution) handler).hasResponseBodyAnnotation();
    }

    @Override
    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return (ModelAndView) ((HandlerExecution) handler).handle(request, response);
    }
}
