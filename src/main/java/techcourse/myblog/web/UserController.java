package techcourse.myblog.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import techcourse.myblog.domain.User;
import techcourse.myblog.domain.dto.LoginRequestDto;
import techcourse.myblog.domain.dto.MyPageRequestDto;
import techcourse.myblog.domain.dto.UserRequestDto;
import techcourse.myblog.domain.service.UserService;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import javax.validation.Valid;

@Controller
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
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

    @ResponseBody
    @PostMapping("/check-email")
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> checkEmailDuplicate(String email) {
        if (userService.isDuplicateEmail(email)) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/users")
    public String selectAllUsers(Model model) {
        model.addAttribute("users", userService.findAll());
        return "user-list";
    }

    @PostMapping("/login")
    public String login(LoginRequestDto loginRequestDto, Model model, HttpSession httpSession) {
        String requestEmail = loginRequestDto.getEmail();
        if (userService.notExistUserEmail(requestEmail)) {
            model.addAttribute("error", true);
            model.addAttribute("message", "존재하지않는 email입니다.");
            return "login";
        }
        if (!userService.matchEmailAndPassword(requestEmail, loginRequestDto.getPassword())) {
            model.addAttribute("error", true);
            model.addAttribute("message", "비밀번호가 일치하지않습니다.");
            return "login";
        }
        httpSession.setAttribute("user", userService.findUserByEmail(requestEmail));
        return "redirect:/";
    }

    @Transactional
    @DeleteMapping("/user/delete/{pageId}")
    public String deleteUser(@PathVariable long pageId, HttpSession httpSession) {
        User pageUser = userService.findUserById(pageId);
        User loggedInUser = (User) httpSession.getAttribute("user");
        if (pageUser.getId() != loggedInUser.getId()) {
            return "redirect:/mypage/" + pageId;
        }

        httpSession.removeAttribute("user");
        userService.deleteById(pageId);
        return "redirect:/";
    }

    @GetMapping("/mypage/{id}")
    public String moveMyPage(@PathVariable long id, Model model) {
        model.addAttribute("pageUser", userService.findUserById(id));
        return "mypage";
    }

    @GetMapping("/user/update/{pageId}")
    public String moveMyPageEdit(@PathVariable long pageId, HttpSession httpSession, Model model) {
        User pageUser = userService.findUserById(pageId);
        User loggedInUser = (User) httpSession.getAttribute("user");
        if (pageUser.getId() != loggedInUser.getId()) {
            return "redirect:/mypage/" + pageId;
        }

        model.addAttribute("pageUser", pageUser);
        return "mypage-edit";
    }

    @Transactional
    @PutMapping("/user/update")
    public String updateMyPage(MyPageRequestDto myPageRequestDto, HttpSession httpSession) {
        User loggedInUser = (User) httpSession.getAttribute("user");
        if (myPageRequestDto.getId() != loggedInUser.getId()) {
            return "redirect:/mypage/" + myPageRequestDto.getId();
        }

        User user = userService.updateName(myPageRequestDto.getId(), myPageRequestDto.getName());
        httpSession.setAttribute("user", user);
        return "redirect:/mypage/" + user.getId();
    }
}
