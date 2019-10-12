package nextstep.mvc;

import nextstep.mvc.tobe.exception.HandlerNotFoundException;
import nextstep.mvc.tobe.exception.ViewResolverNotFoundException;
import nextstep.mvc.tobe.handleradapter.HandlerAdapter;
import nextstep.mvc.tobe.view.ModelAndView;
import nextstep.mvc.tobe.view.View;
import nextstep.mvc.tobe.view.resolver.ViewResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

    private final List<HandlerMapping> handlerMappings;
    private final List<HandlerAdapter> handlerAdapters;
    private final List<ViewResolver> viewResolvers;

    public DispatcherServlet(List<HandlerMapping> handlerMappings, List<HandlerAdapter> handlerAdapters, List<ViewResolver> viewResolvers) {
        this.handlerMappings = handlerMappings;
        this.handlerAdapters = handlerAdapters;
        this.viewResolvers = viewResolvers;
    }

    @Override
    public void init() {
        handlerMappings.forEach(HandlerMapping::initialize);
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.debug("Method : {}, Request URI : {}", request.getMethod(), request.getRequestURI());
        try {
            ModelAndView mav = handleRequest(request, response);
            renderIfView(request, response, mav);
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
                .findAny()
                .get().handle(request, response, handler);
    }

    /**
     * @param request
     * @return Controller or HandlerExecution which matches given request
     * @throws HandlerNotFoundException if handler not found
     */
    private Object selectHandler(HttpServletRequest request) throws HandlerNotFoundException {
        return handlerMappings.stream()
                .map(handlerMapping -> handlerMapping.getHandler(request))
                .filter(Objects::nonNull)
                .findAny()
                .orElseThrow(HandlerNotFoundException::new);
    }

    private void renderIfView(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) throws Exception {
        if (mav != null) {
            render(mav, request, response);
        }
    }

    private void render(ModelAndView mav, HttpServletRequest request, HttpServletResponse response) throws Exception {
        View view;
        String viewName = mav.getViewName();
        if (viewName == null) {
            view = mav.getView();
        } else {
            view = resolveViewName(viewName);
        }
        view.render(mav.getModel(), request, response);
    }

    private View resolveViewName(String viewName) throws ViewResolverNotFoundException {
        return viewResolvers.stream()
                .map(viewResolver -> viewResolver.resolveViewName(viewName))
                .filter(Objects::nonNull)
                .findAny()
                .orElseThrow(ViewResolverNotFoundException::new);
    }
}
