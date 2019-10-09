package webserver.requestresolver;

import http.request.HttpRequest;
import http.response.HttpResponse;
import webserver.view.ModelAndView;

public interface Resolver {
    ModelAndView resolve(HttpRequest request, HttpResponse response) throws Exception;
}
