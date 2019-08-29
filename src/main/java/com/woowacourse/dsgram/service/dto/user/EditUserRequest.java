package com.woowacourse.dsgram.service.dto.user;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Setter
@ToString
public class EditUserRequest {
    @NotNull
    private long id;

    @Size(min = 2, max = 30, message = "닉네임은 2~10자")
    private String nickName;

    @Size(min = 2, max = 30, message = "이름은 2~10자")
    private String userName;

    @Size(min = 4, max = 50, message = "비밀번호는 4~16자")
    private String password;

    private String webSite;
    private String intro;
    private Optional<MultipartFile> file;

    @Builder
    public EditUserRequest(String nickName, String userName, String password, String webSite, String intro, Optional<MultipartFile> file) {
        this.nickName = nickName;
        this.userName = userName;
        this.password = password;
        this.webSite = webSite;
        this.intro = intro;
        this.file = file;
    }
}
