package techcourse.myblog.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import techcourse.myblog.domain.model.User;
import techcourse.myblog.domain.service.UserService;
import techcourse.myblog.dto.MyPageRequestDto;
import techcourse.myblog.dto.UserRequestDto;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static techcourse.myblog.web.SessionManager.USER;

@Controller
@RequestMapping("/users")
public class UserController {
    private static final String USERS = "users";
    private static final String USER = "user";

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("")
    public String createUser(@Valid UserRequestDto userRequestDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "signup";
        }
        userService.save(userRequestDto.toEntity());
        return "redirect:/login";
    }

    @GetMapping("")
    public String selectAllUsers(Model model) {
        model.addAttribute(USERS, userService.findAll());
        return "user-list";
    }

    @GetMapping("/{userId}")
    public String moveMyPage(@PathVariable long userId, Model model) {
        model.addAttribute(USER, userService.findUserById(userId));
        return "mypage";
    }

    @GetMapping("/{userId}/edit")
    public String moveMyPageEdit(@PathVariable long userId, Model model) {
        User user = userService.findUserById(userId);
        model.addAttribute(USER, user);
        return "mypage-edit";
    }

    @PutMapping("/{userId}")
    public RedirectView updateMyPage(@PathVariable long userId, MyPageRequestDto myPageRequestDto, HttpSession httpSession) {
        User user = userService.updateUserInfo(userId, myPageRequestDto);
        httpSession.setAttribute(USER, user);
        return new RedirectView("/users/" + user.getId());
    }

    @DeleteMapping("/{userId}")
    public RedirectView deleteUser(@PathVariable long userId, HttpSession httpSession) {
        httpSession.removeAttribute(USER);
        userService.deleteById(userId);
        return new RedirectView("/");
    }
}
