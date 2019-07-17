package techcourse.myblog.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import techcourse.myblog.domain.UserRepository;
import techcourse.myblog.domain.UserRequestDto;

import javax.transaction.Transactional;
import javax.validation.Valid;

@Controller
public class UserController {
    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/signup")
    public String index() {
        return "signup";
    }

    @Transactional
    @PostMapping("/users")
    public String createUser(@Valid UserRequestDto userRequestDto) {
        userRepository.save(userRequestDto.toEntity());
        return "redirect:/login";
    }
}
