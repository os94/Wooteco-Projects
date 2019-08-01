package techcourse.myblog.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;
import techcourse.myblog.domain.model.User;
import techcourse.myblog.domain.service.LoginService;
import techcourse.myblog.dto.LoginRequestDto;

import javax.servlet.http.HttpSession;

import static techcourse.myblog.web.SessionManager.USER;

@Controller
public class LoginController {
    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping("/login")
    public String moveLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public RedirectView login(LoginRequestDto loginRequestDto, HttpSession httpSession) {
        User user = loginService.login(loginRequestDto.getEmail(), loginRequestDto.getPassword());
        httpSession.setAttribute(USER, user);
        return new RedirectView("/");
    }

    @GetMapping("/logout")
    public RedirectView logout(HttpSession httpSession) {
        httpSession.removeAttribute(USER);
        return new RedirectView("/");
    }
}
