package view;

import http.common.HttpStatus;
import http.response.HttpResponse;
import view.strategy.ResponseBodyStrategy;

public class ViewResolver {
    private final ResponseBodyStrategy strategy;

    public ViewResolver(ResponseBodyStrategy strategy) {
        this.strategy = strategy;
    }

    public void resolve(HttpResponse response, ModelAndView modelAndView) {
        HttpStatus status = modelAndView.getStatus();

        if (HttpStatus.OK == status) {
            response.ok(strategy.render(modelAndView));
            return;
        }
        if (HttpStatus.FOUND == status) {
            response.redirect(modelAndView.getViewName());
            return;
        }
        if (HttpStatus.NOT_FOUND == status) {
            response.notFound(modelAndView.getViewName());
            return;
        }
        if (HttpStatus.INTERNAL_SERVER_ERROR == status) {
            response.internalServerError(modelAndView.getViewName());
            return;
        }
    }
}
