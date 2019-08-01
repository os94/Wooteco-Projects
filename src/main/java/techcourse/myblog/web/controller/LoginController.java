package techcourse.myblog.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;
import techcourse.myblog.domain.model.User;
import techcourse.myblog.domain.service.UserService;
import techcourse.myblog.dto.LoginRequestDto;

import javax.servlet.http.HttpSession;

import static techcourse.myblog.web.SessionManager.SESSION_USER;

@Controller
public class LoginController {
    private final UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String moveLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public RedirectView login(LoginRequestDto loginRequestDto, HttpSession httpSession) {
        User user = userService.login(loginRequestDto.getEmail(), loginRequestDto.getPassword());
        httpSession.setAttribute(SESSION_USER, user);
        return new RedirectView("/");
    }

    @GetMapping("/logout")
    public RedirectView logout(HttpSession httpSession) {
        httpSession.removeAttribute(SESSION_USER);
        return new RedirectView("/");
    }
}
