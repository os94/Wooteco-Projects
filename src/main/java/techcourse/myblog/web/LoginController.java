package techcourse.myblog.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import techcourse.myblog.domain.service.LoginService;
import techcourse.myblog.domain.service.UserService;
import techcourse.myblog.dto.LoginRequestDto;

import javax.servlet.http.HttpSession;

import static techcourse.myblog.web.SessionManager.USER;

@Controller
public class LoginController {
    private static final String ERROR_NOT_EXIST_EMAIL = "존재하지않는 email입니다.";
    private static final String ERROR_MISMATCH_PASSWORD = "비밀번호가 일치하지않습니다.";

    private final LoginService loginService;
    private final UserService userService;

    @Autowired
    public LoginController(LoginService loginService, UserService userService) {
        this.loginService = loginService;
        this.userService = userService;
    }

    @GetMapping("/login")
    public String moveLoginPage(HttpSession httpSession) {
        if (httpSession.getAttribute(USER) != null) {
            return "redirect:/";
        }
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession httpSession) {
        httpSession.removeAttribute(USER);
        return "redirect:/";
    }

    @ResponseBody
    @PostMapping("/check-email")
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> checkEmailDuplicate(String email) {
        if (loginService.isDuplicateEmail(email)) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/login")
    public String login(LoginRequestDto loginRequestDto, Model model, HttpSession httpSession) {
        String requestEmail = loginRequestDto.getEmail();
        if (loginService.notExistUserEmail(requestEmail)) {
            model.addAttribute("error", true);
            model.addAttribute("message", ERROR_NOT_EXIST_EMAIL);
            return "login";
        }
        if (!loginService.matchEmailAndPassword(requestEmail, loginRequestDto.getPassword())) {
            model.addAttribute("error", true);
            model.addAttribute("message", ERROR_MISMATCH_PASSWORD);
            return "login";
        }
        httpSession.setAttribute(USER, userService.findUserByEmail(requestEmail));
        return "redirect:/";
    }
}
