package techcourse.myblog.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import techcourse.myblog.domain.exception.*;
import techcourse.myblog.dto.UserDto;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ControllerExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    // Todo : signup.html-redirect 도전
    @ExceptionHandler(BindException.class)
    public RedirectView bindException(BindException e, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("userDto", new UserDto("", "", ""));
        redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userDto", e.getBindingResult());
        log.debug("e.getBindingResult : {}", e.getBindingResult());
        log.debug("e.getObjectName: {}", e.getObjectName());
        //return new RedirectView(e.getObjectName())
        return new RedirectView("/signup");
    }

    @ExceptionHandler(DuplicateEmailException.class)
    public RedirectView duplicateEmailException(DuplicateEmailException e, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", true);
        redirectAttributes.addFlashAttribute("message", e.getMessage());
        return new RedirectView("/signup");
    }

    @ExceptionHandler(UserNotFoundException.class)
    public String userNotFoundException(UserNotFoundException e, HttpServletRequest request, Model model) {
        String url = request.getRequestURL().toString();
        if (url.contains("/login")) {
            model.addAttribute("error", true);
            model.addAttribute("message", e.getMessage());
            return "/login";
        }
        return "redirect:/";
    }

    @ExceptionHandler(MisMatchPasswordException.class)
    public String misMatchPasswordException(MisMatchPasswordException e, Model model) {
        model.addAttribute("error", true);
        model.addAttribute("message", e.getMessage());
        return "/login";
    }

    @ExceptionHandler(ArticleNotFoundException.class)
    public RedirectView articleNotFoundException(ArticleNotFoundException e) {
        return new RedirectView("/");
    }

    @ExceptionHandler(CommentNotFoundException.class)
    public RedirectView commentNotFoundException(CommentNotFoundException e) {
        return new RedirectView("/");
    }

    @ExceptionHandler(MisMatchUserException.class)
    public RedirectView misMatchAuthorException(MisMatchUserException e, HttpServletRequest request) {
        String url = request.getRequestURL().toString();
        url = url.replace("/edit", "");
        if (url.contains("comments")) {
            int index = url.lastIndexOf("/comment");
            url = url.substring(0, index);
        }
        return new RedirectView(url);
    }
}
