package slipp.controller;

import nextstep.utils.JsonUtils;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import nextstep.web.annotation.ResponseBody;
import slipp.domain.User;
import slipp.dto.UserUpdatedDto;
import slipp.support.db.DataBase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class UserApiController {

    @RequestMapping(value = "/api/users", method = RequestMethod.POST)
    public @ResponseBody
    User create(HttpServletRequest request, HttpServletResponse response) {
        User user = JsonUtils.toObject(request, User.class);
        DataBase.addUser(user);

        response.setStatus(HttpServletResponse.SC_CREATED);
        response.addHeader("Location", "/api/users?userId=" + user.getUserId());
        return user;
    }

    @RequestMapping(value = "/api/users", method = RequestMethod.GET)
    public @ResponseBody
    User find(HttpServletRequest request, HttpServletResponse response) {
        String userId = request.getParameter("userId");
        User user = DataBase.findUserById(userId);
        return user;
    }

    @RequestMapping(value = "/api/users", method = RequestMethod.PUT)
    public @ResponseBody
    User update(HttpServletRequest request, HttpServletResponse response) {
        UserUpdatedDto userUpdatedDto = JsonUtils.toObject(request, UserUpdatedDto.class);
        String userId = request.getParameter("userId");
        User user = DataBase.findUserById(userId);
        user.update(new User(userId, userUpdatedDto.getPassword(), userUpdatedDto.getName(), userUpdatedDto.getEmail()));

        response.addHeader("Location", "/api/users?userId=" + user.getUserId());
        return user;
    }
}
