package nextstep.mvc.tobe.handleradapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import nextstep.mvc.tobe.HandlerExecution;
import nextstep.mvc.tobe.view.ModelAndView;
import nextstep.web.support.MediaType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class ResponseBodyHandlerAdapter implements HandlerAdapter {
    @Override
    public boolean supports(Object handler) {
        return (handler instanceof HandlerExecution) && ((HandlerExecution) handler).hasResponseBodyAnnotation();
    }

    @Override
    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // TODO: 2019-10-12 refactoring
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        PrintWriter writer = response.getWriter();
        Object returnObject = ((HandlerExecution) handler).handle(request, response);
        if (returnObject == null) {
            writer.print("");
            return null;
        }
        writer.print(new ObjectMapper().writeValueAsString(returnObject));
        return null;
    }
}
