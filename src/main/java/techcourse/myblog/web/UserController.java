package techcourse.myblog.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import techcourse.myblog.domain.*;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import javax.validation.Valid;

@Controller
public class UserController {
    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    @PostMapping("/users")
    public String createUser(@Valid UserRequestDto userRequestDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "signup";
        }
        userRepository.save(userRequestDto.toEntity());
        return "redirect:/login";
    }

    @ResponseBody
    @PostMapping("/check-email")
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> checkEmail(String email) {
        int duplicate = userRepository.findUsersByEmail(email).size();

        if (duplicate > 0) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/users")
    public String selectAllUsers(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "user-list";
    }

    @PostMapping("/login")
    public String login(LoginRequestDto loginRequestDto, Model model, HttpSession httpSession) {
        User user = userRepository.findUserByEmail(loginRequestDto.getEmail());
        if (user == null) {
            model.addAttribute("error", true);
            model.addAttribute("message", "존재하지않는 email입니다.");
            return "login";
        }
        if (!user.getPassword().equals(loginRequestDto.getPassword())) {
            model.addAttribute("error", true);
            model.addAttribute("message", "비밀번호가 일치하지않습니다.");
            return "login";
        }
        httpSession.setAttribute("user", user);
        return "redirect:/";
    }

    @Transactional
    @DeleteMapping("/user/delete/{pageId}")
    public String deleteUser(@PathVariable long pageId, HttpSession httpSession) {
        User pageUser = userRepository.findUserById(pageId);
        User loggedInUser = (User) httpSession.getAttribute("user");
        if (pageUser.getId() != loggedInUser.getId()) {
            return "redirect:/mypage/" + pageId;
        }
        httpSession.removeAttribute("user");
        userRepository.deleteById(pageId);
        return "redirect:/";
    }

    @GetMapping("/user/update/{pageId}")
    public String moveMyPageEdit(@PathVariable long pageId, HttpSession httpSession, Model model) {
        User pageUser = userRepository.findUserById(pageId);
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
        User pageUser = userRepository.findUserById(myPageRequestDto.getId());
        User loggedInUser = (User) httpSession.getAttribute("user");
        if (pageUser.getId() != loggedInUser.getId()) {
            return "redirect:/mypage/" + myPageRequestDto.getId();
        }
        pageUser.setName(myPageRequestDto.getName());
        httpSession.setAttribute("user", pageUser);
        return "redirect:/mypage/" + pageUser.getId();
    }
}
