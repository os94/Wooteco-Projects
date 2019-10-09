package view;

import http.request.HttpRequest;
import http.response.HttpResponse;

import java.util.HashMap;
import java.util.Map;

public class ModelAndView2 {
    private View view;
    private Map<String, Object> model = new HashMap<>();

    public ModelAndView2() {
    }

    public ModelAndView2(View view) {
        this.view = view;
    }

    public void render(HttpRequest request, HttpResponse response) throws Exception {
        view.render(model, request, response);
    }

    public ModelAndView2 addObject(String attributeName, Object attributeValue) {
        model.put(attributeName, attributeValue);
        return this;
    }
}
