package webserver.resolver;

import http.request.HttpRequest;
import http.response.HttpResponse;
import view.ModelAndView;

public interface Resolver {
    ModelAndView resolve(HttpRequest request, HttpResponse response);
}
