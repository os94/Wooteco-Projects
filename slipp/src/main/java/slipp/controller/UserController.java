package slipp.controller;

import nextstep.mvc.tobe.view.JspView;
import nextstep.mvc.tobe.view.ModelAndView;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class UserController {

    @RequestMapping(value = "/users/form", method = RequestMethod.GET)
    public ModelAndView showSignUpForm(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView(new JspView("/user/form.jsp"));
    }

    @RequestMapping(value = "/users/loginForm", method = RequestMethod.GET)
    public ModelAndView showLoginForm(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView(new JspView("/user/login.jsp"));
    }
}
