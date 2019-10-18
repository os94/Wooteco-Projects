package slipp.controller;

import nextstep.mvc.asis.Controller;
import slipp.dao.UserDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ProfileController implements Controller {

    private final UserDao userDao = UserDao.getInstance();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String userId = req.getParameter("userId");

        return userDao.findByUserId(userId)
                .map(user -> {
                    req.setAttribute("user", user);
                    return "/user/profile.jsp";
                }).orElseThrow(() -> {
                    throw new NullPointerException("사용자를 찾을 수 없습니다.");
                });
    }
}
