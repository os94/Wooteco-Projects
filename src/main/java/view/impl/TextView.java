package view.impl;

import http.request.HttpRequest;
import http.response.HttpResponse;
import utils.FileIoUtils;
import view.View;

import java.util.Map;

public class TextView implements View {
    private final String viewName;

    public TextView(String viewName) {
        this.viewName = viewName;
    }

    @Override
    public void render(Map<String, ?> model, HttpRequest request, HttpResponse response) throws Exception {
        response.ok(FileIoUtils.loadFileFromClasspath(viewName));
    }
}
