package webserver;

import http.request.HttpRequest;
import http.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.exception.ResourceNotFoundException;
import webserver.exception.UrlNotFoundException;
import webserver.requestresolver.ControllerResolver;
import webserver.requestresolver.ResourceResolver;
import webserver.view.ModelAndView;

public class RequestDispatcher {
    private static final Logger logger = LoggerFactory.getLogger(RequestDispatcher.class);

    private final ResourceResolver resourceResolver = new ResourceResolver();
    private final ControllerResolver controllerResolver = new ControllerResolver();

    public void resolve(HttpRequest request, HttpResponse response) {
        try {
            if (request.requestFile()) {
                ModelAndView mav = resourceResolver.resolve(request, response);
                mav.render(request, response);
                return;
            }
            ModelAndView mav = controllerResolver.resolve(request, response);
            mav.render(request, response);
        } catch (ResourceNotFoundException | UrlNotFoundException e) {
            logger.info("Not Found Exception", e);
            response.notFound(e.getMessage());
        } catch (Exception e) {
            logger.error("Internal Server Error", e);
            response.internalServerError(e.getMessage());
        }
    }
}
