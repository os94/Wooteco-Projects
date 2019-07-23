package techcourse.myblog.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import techcourse.myblog.domain.User;
import techcourse.myblog.domain.service.UserService;
import techcourse.myblog.dto.MyPageRequestDto;
import techcourse.myblog.dto.UserRequestDto;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import javax.validation.Valid;

import static techcourse.myblog.web.SessionManager.USER;

@Controller
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/signup")
    public String moveSignUpPage(UserRequestDto userRequestDto) {
        return "signup";
    }

    @Transactional
    @PostMapping("/users")
    public String createUser(@Valid UserRequestDto userRequestDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "signup";
        }
        userService.save(userRequestDto.toEntity());
        return "redirect:/login";
    }

    @GetMapping("/users")
    public String selectAllUsers(Model model) {
        model.addAttribute("users", userService.findAll());
        return "user-list";
    }

    @GetMapping("/mypage/{id}")
    public String moveMyPage(@PathVariable long id, Model model) {
        model.addAttribute("user", userService.findUserById(id));
        return "mypage";
    }

    @GetMapping("/user/update/{pageId}")
    public String moveMyPageEdit(@PathVariable long pageId, Model model) {
        User user = userService.findUserById(pageId);
        model.addAttribute("user", user);
        return "mypage-edit";
    }

    @Transactional
    @PutMapping("/user/update/{pageId}")
    public String updateMyPage(@PathVariable long pageId, MyPageRequestDto myPageRequestDto, HttpSession httpSession) {
        User user = userService.updateUserInfo(pageId, myPageRequestDto);
        httpSession.setAttribute(USER, user);
        return "redirect:/mypage/" + user.getId();
    }

    @Transactional
    @DeleteMapping("/user/delete/{pageId}")
    public String deleteUser(@PathVariable long pageId, HttpSession httpSession) {
        httpSession.removeAttribute(USER);
        userService.deleteById(pageId);
        return "redirect:/";
    }
}
