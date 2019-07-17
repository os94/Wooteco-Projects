package techcourse.myblog.domain;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserRequestDto {
    private long id;

    @NotBlank(message = "이름을 작성해주세요.")
    @Size(min = 2, max = 10, message = "2자이상 10자미만으로 작성해야 합니다.")
    @Pattern(regexp = "[a-zA-Z가-힣]{2,10}", message = "숫자나 특수문자가 포함되었습니다.")
    private String name;

    //ToDo : 중복 이메일 확인 해야함
    @NotBlank(message = "메일을 작성해주세요.")
    @Email(message = "메일의 양식을 지켜주세요.")
    private String email;

    @NotBlank(message = "비밀번호를 작성해주세요.")
    @Pattern(regexp = "(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}",
            message = "비밀번호는 8자 이상의 소문자, 대문자, 숫자, 특수문자로 이루어져야 합니다.")
    private String password;

    public UserRequestDto() {
    }

    public User toEntity() {
        return new User(name, email, password);
    }

    public long getId() {
        return id;
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

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
