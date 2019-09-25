package model;

import java.util.Objects;

import static org.apache.commons.lang3.StringUtils.isEmpty;

public class User {
    private final String userId;
    private final String password;
    private final String name;
    private final String email;

    public User(String userId, String password, String name, String email) {
        if (isEmpty(userId) || isEmpty(password) || isEmpty(name) || isEmpty(email)) {
            throw new UserCreateException("잘못된 User생성입니다.");
        }
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public boolean matchIdAndPassword(String userId, String password) {
        return this.userId.equals(userId) && this.password.equals(password);
    }

    public boolean matchId(String userId) {
        return this.userId.equals(userId);
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userId, user.userId) &&
                Objects.equals(password, user.password) &&
                Objects.equals(name, user.name) &&
                Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, password, name, email);
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
