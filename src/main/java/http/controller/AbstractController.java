package http.controller;

import http.exception.NotSupportedHttpMethodException;
import http.request.HttpRequest;
import http.response.HttpResponse;

public abstract class AbstractController implements Controller {
    @Override
    public void service(HttpRequest request, HttpResponse response) {
        if (request.isGetMethod()) {
            doGet(request, response);
            return;
        }
        if (request.isPostMethod()) {
            doPost(request, response);
            return;
        }
        throw new NotSupportedHttpMethodException(request.getMethod());
    }

    protected void doGet(HttpRequest request, HttpResponse response) {
        throw new NotSupportedHttpMethodException(request.getMethod());
    }

    protected void doPost(HttpRequest request, HttpResponse response) {
        throw new NotSupportedHttpMethodException(request.getMethod());
    }
}
