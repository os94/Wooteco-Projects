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
import java.util.Optional;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);
    private static final String DEFAULT_REDIRECT_PREFIX = "redirect:";

    private HandlerMapping legacyHandlerMapping;
    private AnnotationHandlerMapping annotationHandlerMapping;

    public DispatcherServlet(HandlerMapping legacyHandlerMapping, Object basePackage) {
        this.legacyHandlerMapping = legacyHandlerMapping;
        this.annotationHandlerMapping = new AnnotationHandlerMapping(basePackage);
    }

    @Override
    public void init() throws ServletException {
        legacyHandlerMapping.initialize();
        annotationHandlerMapping.initialize();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestUri = req.getRequestURI();
        logger.debug("Method : {}, Request URI : {}", req.getMethod(), requestUri);

        Object result = getHandler(req, resp, requestUri);

        if (result instanceof HandlerExecution) {
            try {
                ModelAndView mav = ((HandlerExecution) result).handle(req, resp);
                mav.getView().render(mav.getModel(), req, resp);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (result instanceof Controller) {
            try {
                String viewName = ((Controller) result).execute(req, resp);
                move(viewName, req, resp);
            } catch (Throwable e) {
                logger.error("Exception : {}", e);
                throw new ServletException(e.getMessage());
            }
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    // Controller or HandlerExecution
    private Object getHandler(HttpServletRequest req, HttpServletResponse resp, String requestUri) {
        return Optional.ofNullable(legacyHandlerMapping.getHandler(requestUri))
                .orElseGet(() -> annotationHandlerMapping.getHandler(req));
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
