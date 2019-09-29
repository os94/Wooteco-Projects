package controller;

import http.request.HttpRequest;
import http.response.HttpResponse;
import view.ModelAndView;

public abstract class AbstractController implements Controller {
    @Override
    public ModelAndView service(HttpRequest request, HttpResponse response) {
        // TODO: 2019-09-26 이 분기를 없애볼까?
        if (request.isGetMethod()) {
            return doGet(request, response);
        }
        if (request.isPostMethod()) {
            return doPost(request, response);
        }
        throw new NotSupportedHttpMethodException(request.getMethod());
    }

    protected ModelAndView doGet(HttpRequest request, HttpResponse response) {
        throw new NotSupportedHttpMethodException(request.getMethod());
    }

    protected ModelAndView doPost(HttpRequest request, HttpResponse response) {
        throw new NotSupportedHttpMethodException(request.getMethod());
    }
}
