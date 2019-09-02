package com.woowacourse.dsgram.domain;

import com.woowacourse.dsgram.domain.exception.InvalidUserException;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@EqualsAndHashCode(of = {"id"})
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Email
    @NotBlank
    @Column(nullable = false, unique = true)
    private String email;

    @Size(min = 2, max = 30)
    @Column(nullable = false, unique = true)
    private String nickName;

    @Size(min = 2, max = 30)
    @Column(nullable = false)
    private String userName;

    @Size(min = 4, max = 30)
    @Column(nullable = false)
    private String password;

    @Column(length = 50)
    private String webSite;

    @Lob
    private String intro;

    @OneToOne
    @JoinColumn(name = "FILEINFO_ID")
    private FileInfo fileInfo;

    @Column(columnDefinition = "boolean default false")
    private boolean isOauthUser;

    @Builder
    public User(String email, String nickName, String userName, String password, String webSite, String intro, FileInfo fileInfo, boolean isOauthUser) {
        this.email = email;
        this.nickName = nickName;
        this.userName = userName;
        this.password = password;
        this.webSite = webSite;
        this.intro = intro;
        this.fileInfo = fileInfo;
        this.isOauthUser = isOauthUser;
    }

    public void update(User updatedUser, String sessionEmail) {
        checkEmail(sessionEmail);
        this.nickName = updatedUser.nickName;
        this.userName = updatedUser.userName;
        this.password = updatedUser.password;
        this.webSite = updatedUser.webSite;
        this.intro = updatedUser.intro;
        this.fileInfo = updatedUser.fileInfo;
    }

    public void changeToOAuthUser() {
        if (!this.isOauthUser) {
            this.isOauthUser = true;
        }
    }

    public void checkPassword(String password) {
        if (!this.password.equals(password)) {
            throw new InvalidUserException("회원정보가 일치하지 않습니다.");
        }
    }

    public void checkEmail(String email) {
        if (!this.email.equals(email)) {
            throw new InvalidUserException("회원정보가 일치하지 않습니다.");
        }
    }

    public boolean equalsNickName(String nickName) {
        return this.nickName.equals(nickName);
    }

    public boolean isNotSameUser(long id) {
        return this.id != id;
    }

    public boolean isSameUser(User other) {
        return this.id == other.id;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", nickName='" + nickName + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", webSite='" + webSite + '\'' +
                ", intro='" + intro + '\'' +
                ", fileInfo=" + fileInfo +
                ", isOauthUser=" + isOauthUser +
                '}';
    }
}
