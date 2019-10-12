package slipp.controller;

import nextstep.mvc.tobe.view.impl.JsonView;
import nextstep.mvc.tobe.view.ModelAndView;
import nextstep.utils.JsonUtils;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import nextstep.web.annotation.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import slipp.domain.User;
import slipp.dto.UserUpdatedDto;
import slipp.support.db.DataBase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

@Controller
public class UserApiController {

    private static final Logger logger = LoggerFactory.getLogger(UserApiController.class);

    @RequestMapping(value = "/api/users", method = RequestMethod.POST)
    public ModelAndView create(HttpServletRequest request, HttpServletResponse response) {
        User user = parseBody(request, User.class);
        DataBase.addUser(user);

        response.setStatus(HttpServletResponse.SC_CREATED);
        response.addHeader("Location", "/api/users?userId=" + user.getUserId());
        return new ModelAndView();
    }

    @RequestMapping(value = "/api/users", method = RequestMethod.GET)
    public @ResponseBody User find(HttpServletRequest request, HttpServletResponse response) {
        String userId = request.getParameter("userId");
        User user = DataBase.findUserById(userId);

        response.setStatus(HttpServletResponse.SC_OK);
        return user;
    }

    @RequestMapping(value = "/api/users", method = RequestMethod.PUT)
    public ModelAndView update(HttpServletRequest request, HttpServletResponse response) {
        UserUpdatedDto userUpdatedDto = parseBody(request, UserUpdatedDto.class);
        String userId = request.getParameter("userId");
        User user = DataBase.findUserById(userId);
        user.update(new User(userId, userUpdatedDto.getPassword(), userUpdatedDto.getName(), userUpdatedDto.getEmail()));

        response.setStatus(HttpServletResponse.SC_OK);
        response.addHeader("Location", "/api/users?userId=" + user.getUserId());
        return new ModelAndView();
    }

    private <T> T parseBody(HttpServletRequest request, Class<T> clazz) {
        try {
            return JsonUtils.toObject(request.getReader().lines().collect(Collectors.joining()), clazz);
        } catch (IOException e) {
            logger.error("Error occurred during parsing json body", e);
            throw new RuntimeException(e);
        }
    }
}
