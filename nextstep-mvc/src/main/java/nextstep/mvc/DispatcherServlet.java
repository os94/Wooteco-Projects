package nextstep.mvc;

import nextstep.mvc.tobe.AnnotationHandlerMapping;
import nextstep.mvc.tobe.handleradapter.AnnotationHandlerAdapter;
import nextstep.mvc.tobe.handleradapter.HandlerAdapter;
import nextstep.mvc.tobe.handleradapter.ManualHandlerAdapter;
import nextstep.mvc.tobe.view.ModelAndView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

    private final List<HandlerMapping> handlerMappings = new ArrayList<>();
    private final List<HandlerAdapter> handlerAdapters = new ArrayList<>();

    public DispatcherServlet(HandlerMapping legacyHandlerMapping, Object basePackage) {
        handlerMappings.add(legacyHandlerMapping);
        handlerMappings.add(new AnnotationHandlerMapping(basePackage));

        handlerAdapters.add(new ManualHandlerAdapter());
        handlerAdapters.add(new AnnotationHandlerAdapter());
    }

    @Override
    public void init() {
        handlerMappings.stream()
                .forEach(handlerMapping -> handlerMapping.initialize());
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {
        logger.debug("Method : {}, Request URI : {}", req.getMethod(), req.getRequestURI());
        Optional<Object> maybeHandler = selectHandler(req);

        maybeHandler.ifPresentOrElse(handler -> {
            try {
                ModelAndView mav = handlerAdapters.stream()
                        .filter(adapter -> adapter.supports(handler))
                        .findFirst()
                        .get().handle(req, resp, handler);
                mav.getView().render(mav.getModel(), req, resp);
            } catch (Exception e) {
                logger.error(e.getMessage());
                throw new RuntimeException(e);
            }
        }, () -> {
            try {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            } catch (IOException e) {
                logger.error(e.getMessage());
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * @param req
     * @return Controller or HandlerExecution which matches given request
     */
    private Optional<Object> selectHandler(HttpServletRequest req) {
        return handlerMappings.stream()
                .map(handlerMapping -> handlerMapping.getHandler(req))
                .filter(handler -> handler != null)
                .findAny();
    }
}
