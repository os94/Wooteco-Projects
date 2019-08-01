package techcourse.myblog.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static techcourse.myblog.dto.UserDto.EMAIL_BLANK_ERROR;

public class LoginDto {
    @NotBlank(message = EMAIL_BLANK_ERROR)
    private String email;

    @NotNull
    private String password;

    public LoginDto(String email, String password) {
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
