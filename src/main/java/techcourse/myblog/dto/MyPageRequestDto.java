package techcourse.myblog.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static techcourse.myblog.dto.UserRequestDto.NAME_BLANK_ERROR;

public class MyPageRequestDto {
    @NotBlank(message = NAME_BLANK_ERROR)
    private String name;

    @NotNull
    private String snsGithub;

    @NotNull
    private String snsFacebook;

    public MyPageRequestDto(String name, String snsGithub, String snsFacebook) {
        this.name = name;
        this.snsGithub = snsGithub;
        this.snsFacebook = snsFacebook;
    }

    public String getName() {
        return name;
    }

    public String getSnsGithub() {
        return snsGithub;
    }

    public String getSnsFacebook() {
        return snsFacebook;
    }
}
