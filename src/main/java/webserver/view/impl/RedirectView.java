package webserver.view.impl;

import http.request.HttpRequest;
import http.response.HttpResponse;
import webserver.view.View;

import java.util.Map;

public class RedirectView implements View {
    private final String viewName;

    public RedirectView(String viewName) {
        this.viewName = viewName;
    }

    @Override
    public void render(Map<String, ?> model, HttpRequest request, HttpResponse response) {
        response.redirect(viewName);
    }
}
