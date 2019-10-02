package nextstep.mvc;

import nextstep.mvc.asis.Controller;
import nextstep.mvc.tobe.AnnotationHandlerMapping;
import nextstep.mvc.tobe.HandlerExecution;
import nextstep.mvc.tobe.ModelAndView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
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
    private static final String DEFAULT_REDIRECT_PREFIX = "redirect:";

    private List<HandlerMapping> handlerMappings = new ArrayList<>();

    public DispatcherServlet(HandlerMapping legacyHandlerMapping, Object basePackage) {
        handlerMappings.add(legacyHandlerMapping);
        handlerMappings.add(new AnnotationHandlerMapping(basePackage));
    }

    @Override
    public void init() {
        handlerMappings.stream()
                .forEach(handlerMapping -> handlerMapping.initialize());
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.debug("Method : {}, Request URI : {}", req.getMethod(), req.getRequestURI());
        Optional<Object> maybeHandler = selectHandler(req);

        // TODO: 2019-10-02 refactoring
        maybeHandler.ifPresentOrElse(handler -> {
            try {
                if (handler instanceof HandlerExecution) {
                    ModelAndView mav = ((HandlerExecution) handler).handle(req, resp);
                    mav.getView().render(mav.getModel(), req, resp);
                } else if (handler instanceof Controller) {
                    String viewName = ((Controller) handler).execute(req, resp);
                    move(viewName, req, resp);
                }
            } catch (Exception e) {
                logger.error(e.getMessage());
                //resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        }, () -> {
            try {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            } catch (IOException e) {
                logger.error(e.getMessage());
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

    private void move(String viewName, HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if (viewName.startsWith(DEFAULT_REDIRECT_PREFIX)) {
            resp.sendRedirect(viewName.substring(DEFAULT_REDIRECT_PREFIX.length()));
            return;
        }

        RequestDispatcher rd = req.getRequestDispatcher(viewName);
        rd.forward(req, resp);
    }
}
