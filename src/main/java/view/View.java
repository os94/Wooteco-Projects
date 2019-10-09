package view;

import http.request.HttpRequest;
import http.response.HttpResponse;

import java.util.Map;

public interface View {
    void render(Map<String, ?> model, HttpRequest request, HttpResponse response) throws Exception;
}
