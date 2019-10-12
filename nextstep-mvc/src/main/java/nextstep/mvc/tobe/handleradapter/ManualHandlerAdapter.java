package nextstep.mvc.tobe.handleradapter;

import nextstep.mvc.asis.Controller;
import nextstep.mvc.tobe.view.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ManualHandlerAdapter implements HandlerAdapter {
    @Override
    public boolean supports(Object handler) {
        return handler instanceof Controller;
    }

    @Override
    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String viewName = ((Controller) handler).execute(request, response);
        return new ModelAndView(viewName);
    }
}
