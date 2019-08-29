package com.woowacourse.dsgram.web.controller;

import com.woowacourse.dsgram.service.UserService;
import com.woowacourse.dsgram.service.dto.user.LoggedInUser;
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
                               @UserSession LoggedInUser loggedInUser) {
        model.addAttribute("user", userService.findUserInfoById(userId, loggedInUser));

        return "user-edit";
    }

    @GetMapping("/logout")
    public String logout(HttpSession httpSession) {
        httpSession.removeAttribute(LoggedInUser.SESSION_USER);
        return "redirect:/login";
    }

    @GetMapping("/oauth")
    public String test(@RequestParam String code, HttpSession httpSession) {
        httpSession.setAttribute(LoggedInUser.SESSION_USER, userService.oauth(code));
        return "redirect:/login";
    }
}
