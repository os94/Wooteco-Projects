package nextstep.mvc.tobe.view.impl;

import nextstep.mvc.tobe.view.View;
import nextstep.web.support.MediaType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class TextView implements View {
    private static final TextView EMPTY_VIEW = new TextView("");

    private final String contents;

    public TextView(String contents) {
        this.contents = contents;
    }

    public static TextView emptyView() {
        return EMPTY_VIEW;
    }

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType(MediaType.TEXT_PLAIN);
        response.getWriter().print(contents);
    }
}
