package com.woowacourse.dsgram.web.controller;

import com.woowacourse.dsgram.domain.User;
import com.woowacourse.dsgram.service.UserService;
import com.woowacourse.dsgram.service.dto.user.AuthUserRequest;
import com.woowacourse.dsgram.service.dto.user.EditUserRequest;
import com.woowacourse.dsgram.service.dto.user.LoggedInUser;
import com.woowacourse.dsgram.service.dto.user.SignUpUserRequest;
import com.woowacourse.dsgram.web.argumentresolver.UserSession;
import com.woowacourse.dsgram.web.controller.exception.InvalidPatternException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static com.woowacourse.dsgram.service.dto.user.LoggedInUser.SESSION_USER;

@RestController
@RequestMapping("/api/users")
public class UserApiController {
    private final UserService userService;

    public UserApiController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity signUp(@RequestBody @Valid SignUpUserRequest signUpUserRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            FieldError fieldError = bindingResult.getFieldError();
            throw new InvalidPatternException(fieldError.getDefaultMessage());
        }
        userService.save(signUpUserRequest);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{userId}")
    public ResponseEntity update(@PathVariable long userId,
                                 @Valid EditUserRequest editUserRequest,
                                 BindingResult bindingResult,
                                 @UserSession LoggedInUser loggedInUser,
                                 HttpSession httpSession) {
        if (bindingResult.hasErrors()) {
            FieldError fieldError = bindingResult.getFieldError();
            throw new RuntimeException(fieldError.getDefaultMessage());
        }

        loggedInUser = userService.update(userId, editUserRequest, loggedInUser);
        httpSession.setAttribute(SESSION_USER, loggedInUser);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthUserRequest authUserRequest, HttpSession httpSession) {
        httpSession.setAttribute(SESSION_USER, userService.login(authUserRequest));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity deleteUser(@PathVariable long userId,
                                     @UserSession LoggedInUser loggedInUser,
                                     HttpSession httpSession) {
        userService.deleteUserById(userId, loggedInUser);
        httpSession.removeAttribute(SESSION_USER);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{userId}/image")
    public ResponseEntity<byte[]> showProfileImage(@PathVariable long userId) {
        User user = userService.findUserById(userId);
        if (user.getFileInfo() == null) {
            return ResponseEntity.ok(null);
        }

        return ResponseEntity.ok(userService.findProfileImageById(userId));
    }
}
