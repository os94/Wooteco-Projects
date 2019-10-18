package slipp.controller;

import nextstep.mvc.asis.Controller;
import slipp.dao.UserDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginController implements Controller {

    private final UserDao userDao = UserDao.getInstance();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String userId = req.getParameter("userId");
        String password = req.getParameter("password");

        return userDao.findByUserId(userId)
                .filter(user -> user.matchPassword(password))
                .map(user -> {
                    HttpSession session = req.getSession();
                    session.setAttribute(UserSessionUtils.USER_SESSION_KEY, user);
                    return "redirect:/";
                }).orElseGet(() -> {
                    req.setAttribute("loginFailed", true);
                    return "/user/login.jsp";
                });
    }
}
