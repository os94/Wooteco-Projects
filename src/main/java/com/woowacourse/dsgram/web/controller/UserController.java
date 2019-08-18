package com.woowacourse.dsgram.web.controller;

import com.woowacourse.dsgram.service.UserService;
import com.woowacourse.dsgram.service.dto.user.LoginUserRequest;
import com.woowacourse.dsgram.web.argumentresolver.UserSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/signup")
    public String showSignUp() {
        return "signup";
    }

    @GetMapping("/login")
    public String showLogin() {
        return "login";
    }

    @GetMapping("/users/{userId}/edit")
    public String showUserEdit(@PathVariable long userId,
                               Model model,
                               @UserSession LoginUserRequest loginUserRequest) {
        model.addAttribute("user", userService.findUserInfoById(userId, loginUserRequest));
        return "account-edit";
    }

    @GetMapping("/logout")
    public String logout(HttpSession httpSession) {
        httpSession.removeAttribute("sessionUser");
        return "redirect:/login";
    }

    @GetMapping("/oauth")
    public String test(@RequestParam String code, HttpSession httpSession) {
        httpSession.setAttribute("sessionUser", userService.oauth(code));
        return "redirect:/login";
    }
}
