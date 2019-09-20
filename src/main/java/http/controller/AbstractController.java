package http.controller;

import http.HttpRequest;
import http.HttpResponse;

public abstract class AbstractController implements Controller {
    @Override
    public void service(HttpRequest request, HttpResponse response) {
        if (request.isGetMethod()) {
            doGet(request, response);
            return;
        }
        if (request.isPostMethod()) {
            doPost(request, response);
        }
    }

    public abstract void doGet(HttpRequest request, HttpResponse response);

    public abstract void doPost(HttpRequest request, HttpResponse response);
}
