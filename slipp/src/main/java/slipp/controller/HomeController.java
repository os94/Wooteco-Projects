package slipp.controller;

import nextstep.mvc.tobe.view.impl.JspView;
import nextstep.mvc.tobe.view.ModelAndView;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import slipp.support.db.DataBase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class HomeController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView showMainPage(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("users", DataBase.findAll());
        return new ModelAndView(new JspView("home.jsp"));
    }
}
