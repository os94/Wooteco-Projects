package nextstep.mvc;

import nextstep.mvc.tobe.exception.HandlerNotFoundException;
import nextstep.mvc.tobe.handleradapter.HandlerAdapter;
import nextstep.mvc.tobe.view.ModelAndView;
import nextstep.mvc.tobe.view.View;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

    private final List<HandlerMapping> handlerMappings;
    private final List<HandlerAdapter> handlerAdapters;

    public DispatcherServlet(List<HandlerMapping> handlerMappings, List<HandlerAdapter> handlerAdapters) {
        this.handlerMappings = handlerMappings;
        this.handlerAdapters = handlerAdapters;
    }

    @Override
    public void init() {
        handlerMappings.forEach(handlerMapping -> handlerMapping.initialize());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.debug("Method : {}, Request URI : {}", request.getMethod(), request.getRequestURI());
        try {
            ModelAndView mav = handleRequest(request, response);
            mav.render(request, response);
        } catch (HandlerNotFoundException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        } catch (Exception e) {
            logger.error("Error while handling request", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Object handler = selectHandler(request);
        return handlerAdapters.stream()
                .filter(adapter -> adapter.supports(handler))
                .findFirst()
                .get().handle(request, response, handler);
    }

    /**
     * @param request
     * @return Controller or HandlerExecution which matches given request
     * @throws HandlerNotFoundException if handler not found
     */
    private Object selectHandler(HttpServletRequest request) {
        return handlerMappings.stream()
                .map(handlerMapping -> handlerMapping.getHandler(request))
                .filter(handler -> handler != null)
                .findAny()
                .orElseThrow(HandlerNotFoundException::new);
    }
}
