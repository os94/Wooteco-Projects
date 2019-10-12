package nextstep.mvc.tobe.view.resolver;

import nextstep.mvc.tobe.view.View;

public interface ViewResolver {
    View resolveViewName(String viewName);
}
