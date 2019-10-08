package nextstep.mvc;

import nextstep.mvc.tobe.exception.HandlerNotFoundException;
import nextstep.mvc.tobe.handleradapter.HandlerAdapter;
import nextstep.mvc.tobe.view.ModelAndView;
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
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.debug("Method : {}, Request URI : {}", req.getMethod(), req.getRequestURI());
        try {
            ModelAndView mav = handleRequest(req, resp);
            mav.getView().render(mav.getModel(), req, resp);
        } catch (HandlerNotFoundException e) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        } catch (Exception e) {
            logger.error("Error while handling request", e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private ModelAndView handleRequest(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Object handler = selectHandler(req);
        return handlerAdapters.stream()
                .filter(adapter -> adapter.supports(handler))
                .findFirst()
                .get().handle(req, resp, handler);
    }

    /**
     * @param req
     * @return Controller or HandlerExecution which matches given request
     * @throws HandlerNotFoundException if handler not found
     */
    private Object selectHandler(HttpServletRequest req) {
        return handlerMappings.stream()
                .map(handlerMapping -> handlerMapping.getHandler(req))
                .filter(handler -> handler != null)
                .findAny()
                .orElseThrow(HandlerNotFoundException::new);
    }
}
