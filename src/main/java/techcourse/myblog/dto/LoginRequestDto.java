package techcourse.myblog.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static techcourse.myblog.dto.UserRequestDto.EMAIL_BLANK_ERROR;

public class LoginRequestDto {
    @NotBlank(message = EMAIL_BLANK_ERROR)
    private String email;

    @NotNull
    private String password;

    public LoginRequestDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
