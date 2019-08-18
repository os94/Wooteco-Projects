package com.woowacourse.dsgram.web.controller;

import com.woowacourse.dsgram.service.UserService;
import com.woowacourse.dsgram.service.dto.user.AuthUserRequest;
import com.woowacourse.dsgram.service.dto.user.LoginUserRequest;
import com.woowacourse.dsgram.service.dto.user.SignUpUserRequest;
import com.woowacourse.dsgram.service.dto.user.UserDto;
import com.woowacourse.dsgram.web.argumentresolver.UserSession;
import com.woowacourse.dsgram.web.controller.exception.InvalidPatternException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

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
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("/{userId}")
    public ResponseEntity update(@PathVariable long userId,
                                 @RequestBody @Valid UserDto updatedUserDto,
                                 BindingResult bindingResult,
                                 @UserSession LoginUserRequest loginUserRequest) {
        if (bindingResult.hasErrors()) {
            FieldError fieldError = bindingResult.getFieldError();
            throw new RuntimeException(fieldError.getDefaultMessage());
        }
        userService.update(userId, updatedUserDto, loginUserRequest);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthUserRequest authUserRequest, HttpSession httpSession) {
        httpSession.setAttribute("sessionUser", userService.login(authUserRequest));
        return new ResponseEntity(HttpStatus.OK);
    }
}
