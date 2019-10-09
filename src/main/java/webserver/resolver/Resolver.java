package webserver.resolver;

import http.request.HttpRequest;
import http.response.HttpResponse;
import view.ModelAndView2;

public interface Resolver {
    ModelAndView2 resolve2(HttpRequest request, HttpResponse response) throws Exception;
}
