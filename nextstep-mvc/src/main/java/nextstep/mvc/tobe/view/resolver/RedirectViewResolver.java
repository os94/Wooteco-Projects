package nextstep.mvc.tobe.view.resolver;

import nextstep.mvc.tobe.view.View;
import nextstep.mvc.tobe.view.impl.RedirectView;

public class RedirectViewResolver implements ViewResolver {

    private static final String PREFIX = "redirect:";

    @Override
    public View resolveViewName(String viewName) {
        if (viewName.startsWith(PREFIX)) {
            return new RedirectView(viewName.replace(PREFIX, ""));
        }
        return null;
    }
}
