package techcourse.myblog.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import techcourse.myblog.domain.dto.UserRequestDto;
import techcourse.myblog.domain.service.ArticleService;

import javax.servlet.http.HttpSession;

import static techcourse.myblog.web.SessionManager.USER;

@Controller
public class MainController {
    private final ArticleService articleService;

    @Autowired
    public MainController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/")
    public String moveMainPage(Model model) {
        model.addAttribute("articles", articleService.findAll());
        return "index";
    }

    @GetMapping("/writing")
    public String moveArticleWritePage() {
        return "article-edit";
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

    @GetMapping("/signup")
    public String moveSignUpPage(UserRequestDto userRequestDto) {
        return "signup";
    }
}
