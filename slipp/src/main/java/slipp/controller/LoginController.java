package slipp.controller;

import nextstep.mvc.tobe.view.ModelAndView;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import slipp.domain.User;
import slipp.support.UserSessionUtils;
import slipp.support.db.DataBase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @RequestMapping(value = "/users/loginForm", method = RequestMethod.GET)
    public ModelAndView showLoginForm(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView("/user/login.jsp");
    }

    @RequestMapping(value = "/users/login", method = RequestMethod.POST)
    public ModelAndView login(HttpServletRequest request, HttpServletResponse response) {
        String userId = request.getParameter("userId");
        String password = request.getParameter("password");
        User user = DataBase.findUserById(userId);
        if (user == null) {
            request.setAttribute("loginFailed", true);
            return new ModelAndView("/user/login.jsp");
        }
        if (user.matchPassword(password)) {
            HttpSession session = request.getSession();
            session.setAttribute(UserSessionUtils.USER_SESSION_KEY, user);
            return new ModelAndView("redirect:/");
        } else {
            request.setAttribute("loginFailed", true);
            return new ModelAndView("/user/login.jsp");
        }
    }

    @RequestMapping(value = "/users/logout", method = RequestMethod.GET)
    public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        session.removeAttribute(UserSessionUtils.USER_SESSION_KEY);
        return new ModelAndView("redirect:/");
    }
}
