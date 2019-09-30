package webserver.resolver;

import http.request.HttpRequest;
import http.response.HttpResponse;
import view.ModelAndView;
import view.ViewResolver;
import view.strategy.ResourceFileStrategy;
import view.strategy.TemplateStrategy;

public class RequestResolver {
    private final ResourceResolver resourceResolver = new ResourceResolver();
    private final ControllerResolver controllerResolver = new ControllerResolver();
    private ViewResolver viewResolver;

    public void resolve(HttpRequest request, HttpResponse response) {
        ModelAndView modelAndView;
        if (request.requestFile()) {
            modelAndView = resourceResolver.resolve(request, response);
            viewResolver = new ViewResolver(new ResourceFileStrategy());
            viewResolver.resolve(response, modelAndView);
            return;
        }
        modelAndView = controllerResolver.resolve(request, response);
        viewResolver = new ViewResolver(new TemplateStrategy());
        viewResolver.resolve(response, modelAndView);
    }
}
