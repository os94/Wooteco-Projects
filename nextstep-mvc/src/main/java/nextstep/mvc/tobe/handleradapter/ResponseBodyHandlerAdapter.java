package nextstep.mvc.tobe.handleradapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import nextstep.mvc.tobe.HandlerExecution;
import nextstep.mvc.tobe.view.ModelAndView;
import nextstep.web.support.MediaType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ResponseBodyHandlerAdapter implements HandlerAdapter {

    private final ObjectMapper objectMapper;

    public ResponseBodyHandlerAdapter() {
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public boolean supports(Object handler) {
        return (handler instanceof HandlerExecution) && ((HandlerExecution) handler).hasResponseBodyAnnotation();
    }

    @Override
    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        Object returnObject = ((HandlerExecution) handler).handle(request, response);
        objectMapper.writeValue(response.getWriter(), returnObject);
        return null;
    }
}
