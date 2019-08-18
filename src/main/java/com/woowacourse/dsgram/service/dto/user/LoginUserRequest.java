package com.woowacourse.dsgram.service.dto.user;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginUserRequest {
    private String email;
    private String nickName;
    private String userName;

    @Builder
    public LoginUserRequest(String email, String nickName, String userName) {
        this.email = email;
        this.nickName = nickName;
        this.userName = userName;
    }
}
