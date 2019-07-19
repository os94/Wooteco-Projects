package techcourse.myblog.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import techcourse.myblog.domain.ArticleRepository;
import techcourse.myblog.domain.User;
import techcourse.myblog.domain.UserRepository;
import techcourse.myblog.domain.UserRequestDto;

import javax.servlet.http.HttpSession;

@Controller
public class MainController {
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;

    @Autowired
    public MainController(ArticleRepository articleRepository, UserRepository userRepository) {
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("articles", articleRepository.findAll());
        return "index";
    }

    @GetMapping("/writing")
    public String moveWritePage() {
        return "article-edit";
    }

    @GetMapping("/login")
    public String moveLoginPage(HttpSession httpSession) {
        if (httpSession.getAttribute("user") != null) {
            return "redirect:/";
        }
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession httpSession) {
        httpSession.removeAttribute("user");
        return "redirect:/";
    }

    @GetMapping("/signup")
    public String index(UserRequestDto userRequestDto) {
        return "signup";
    }

    @GetMapping("/mypage/{id}")
    public String moveMyPage(@PathVariable long id, Model model) {
        User pageUser = userRepository.findUserById(id);
        model.addAttribute("pageUser", pageUser);
        return "mypage";
    }
}
