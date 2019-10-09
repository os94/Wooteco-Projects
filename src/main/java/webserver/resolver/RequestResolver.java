package webserver.resolver;

import http.request.HttpRequest;
import http.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import view.ModelAndView2;
import webserver.exception.ResourceNotFoundException;
import webserver.exception.UrlNotFoundException;

public class RequestResolver {
    private static final Logger logger = LoggerFactory.getLogger(RequestResolver.class);

    private final ResourceResolver resourceResolver = new ResourceResolver();
    private final ControllerResolver controllerResolver = new ControllerResolver();

    public void resolve(HttpRequest request, HttpResponse response) {
        try {
            if (request.requestFile()) {
                ModelAndView2 mav2 = resourceResolver.resolve2(request, response);
                mav2.render(request, response);
                return;
            }
            ModelAndView2 mav2 = controllerResolver.resolve2(request, response);
            mav2.render(request, response);
        } catch (ResourceNotFoundException | UrlNotFoundException e) {
            logger.info("Not Found Exception", e);
            response.notFound(e.getMessage());
        } catch (Exception e) {
            logger.error("Internal Server Error", e);
            response.internalServerError(e.getMessage());
        }
    }
}
