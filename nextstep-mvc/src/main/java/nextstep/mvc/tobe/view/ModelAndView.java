package nextstep.mvc.tobe.view;

import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.unmodifiableMap;

public class ModelAndView {
    private Object view;
    private Map<String, Object> model = new HashMap<>();

    public ModelAndView() {
    }

    public ModelAndView(String viewName) {
        this.view = viewName;
    }

    public ModelAndView addObject(String attributeName, Object attributeValue) {
        model.put(attributeName, attributeValue);
        return this;
    }

    public String getViewName() {
        if (view instanceof String) {
            return String.valueOf(view);
        }
        return null;
    }

    public View getView() {
        if (view instanceof View) {
            return (View) view;
        }
        return null;
    }

    public Object getObject(String attributeName) {
        return model.get(attributeName);
    }

    public Map<String, Object> getModel() {
        return unmodifiableMap(model);
    }
}
