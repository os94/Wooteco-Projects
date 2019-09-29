package view;

import http.common.HttpStatus;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ModelAndView {
    private String viewName;
    private Map<String, Object> models;
    private HttpStatus status;

    public ModelAndView(String viewName, HttpStatus status) {
        this.viewName = viewName;
        this.models = new HashMap<>();
        this.status = status;
    }

    public ModelAndView addObject(String attributeName, Object attributeValue) {
        models.put(attributeName, attributeValue);
        return this;
    }

    public String getViewName() {
        return viewName;
    }

    public Map<String, Object> getModels() {
        return models;
    }

    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ModelAndView that = (ModelAndView) o;
        return Objects.equals(viewName, that.viewName) &&
                Objects.equals(models, that.models);
    }

    @Override
    public int hashCode() {
        return Objects.hash(viewName, models);
    }

    @Override
    public String toString() {
        return "ModelAndView{" +
                "viewName='" + viewName + '\'' +
                ", models=" + models +
                '}';
    }
}
