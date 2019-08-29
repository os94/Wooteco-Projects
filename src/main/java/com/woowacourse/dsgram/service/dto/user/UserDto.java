package com.woowacourse.dsgram.service.dto.user;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ToString
public class UserDto {
    @NotNull
    private long id;

    @Size(min = 2, max = 10, message = "닉네임은 2~10자")
    private String nickName;

    @Size(min = 2, max = 10, message = "이름은 2~10자")
    private String userName;

    @Size(min = 4, max = 16, message = "비밀번호는 4~16자")
    private String password;

    private String webSite;
    private String intro;

    @Builder
    public UserDto(long id, String userName, String nickName, String password, String webSite, String intro) {
        this.id = id;
        this.userName = userName;
        this.nickName = nickName;
        this.password = password;
        this.webSite = webSite;
        this.intro = intro;
    }
}
