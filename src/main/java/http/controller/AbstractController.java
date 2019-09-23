package http.controller;

import http.exception.InvalidHeaderException;
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
        throw new InvalidHeaderException("요청이 적절한 HTTP METHOD가 아닙니다.");
    }

    protected void doGet(HttpRequest request, HttpResponse response) {
        triggerException();
    }

    protected void doPost(HttpRequest request, HttpResponse response) {
        triggerException();
    }

    private void triggerException() {
        throw new InvalidHeaderException("유효하지않은 메소드 호출입니다.");
    }
}
