package nextstep.mvc.tobe.view.resolver;

import nextstep.mvc.tobe.view.View;
import nextstep.mvc.tobe.view.impl.JspView;

public class JspViewResolver implements ViewResolver {

    private static final String SUFFIX = ".jsp";

    @Override
    public View resolveViewName(String viewName) {
        if (viewName.endsWith(SUFFIX)) {
            return new JspView(viewName);
        }
        return null;
    }
}
