package com.woowacourse.dsgram.service.dto.user;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthUserRequest {
    private String email;
    private String password;

    public AuthUserRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
