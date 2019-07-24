package techcourse.myblog.dto;

import techcourse.myblog.domain.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserRequestDto {
    public static final String NAME_LENGTH_ERROR = "2자이상 10자미만으로 작성해야 합니다.";
    public static final String NAME_FORMAT_ERROR = "숫자나 특수문자가 포함되었습니다.";
    public static final String NAME_BLANK_ERROR = "이름을 작성해주세요.";
    public static final String EMAIL_FORMAT_ERROR = "메일의 양식을 지켜주세요.";
    public static final String EMAIL_BLANK_ERROR = "메일을 작성해주세요.";
    public static final String PASSWORD_FORMAT_ERROR = "비밀번호는 8자 이상의 소문자, 대문자, 숫자, 특수문자로 이루어져야 합니다.";
    public static final String PASSWORD_BLANK_ERROR = "비밀번호를 작성해주세요.";
    private static final String PASSWORD_REGEXP = "(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}";
    private static final String NAME_REGEXP = "[a-zA-Z가-힣]{2,10}";

    @NotBlank(message = NAME_BLANK_ERROR)
    @Size(min = 2, max = 10, message = NAME_LENGTH_ERROR)
    @Pattern(regexp = NAME_REGEXP, message = NAME_FORMAT_ERROR)
    private String name;

    @NotBlank(message = EMAIL_BLANK_ERROR)
    @Email(message = EMAIL_FORMAT_ERROR)
    private String email;

    @NotBlank(message = PASSWORD_BLANK_ERROR)
    @Pattern(regexp = PASSWORD_REGEXP, message = PASSWORD_FORMAT_ERROR)
    private String password;

    public UserRequestDto(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public User toEntity() {
        return new User(name, email, password);
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
