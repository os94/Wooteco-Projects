package techcourse.myblog.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import techcourse.myblog.domain.dto.LoginRequestDto;
import techcourse.myblog.domain.service.LoginService;
import techcourse.myblog.domain.service.UserService;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {
    private final LoginService loginService;
    private final UserService userService;

    @Autowired
    public LoginController(LoginService loginService, UserService userService) {
        this.loginService = loginService;
        this.userService = userService;
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
            model.addAttribute("message", "존재하지않는 email입니다.");
            return "login";
        }
        if (!loginService.matchEmailAndPassword(requestEmail, loginRequestDto.getPassword())) {
            model.addAttribute("error", true);
            model.addAttribute("message", "비밀번호가 일치하지않습니다.");
            return "login";
        }
        httpSession.setAttribute("user", userService.findUserByEmail(requestEmail));
        return "redirect:/";
    }
}
