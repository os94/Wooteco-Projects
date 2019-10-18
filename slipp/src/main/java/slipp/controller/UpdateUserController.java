package slipp.controller;

import nextstep.mvc.asis.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import slipp.dao.UserDao;
import slipp.dto.UserUpdatedDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UpdateUserController implements Controller {
    private static final Logger log = LoggerFactory.getLogger(UpdateUserController.class);

    private final UserDao userDao = UserDao.getInstance();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        UserUpdatedDto updateUser = new UserUpdatedDto(
                req.getParameter("password"),
                req.getParameter("name"),
                req.getParameter("email"));

        return userDao.findByUserId(req.getParameter("userId"))
                .filter(user -> UserSessionUtils.isSameUser(req.getSession(), user))
                .map(user -> {
                    log.debug("Update User : {}", updateUser);
                    user.update(updateUser);
                    userDao.update(user);
                    return "redirect:/";
                }).orElseThrow(() -> new IllegalStateException("다른 사용자의 정보를 수정할 수 없습니다."));
    }
}
