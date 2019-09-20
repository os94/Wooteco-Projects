package model;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

public class User {
    private final String userId;
    private final String password;
    private final String name;
    private final String email;

    public User(String userId, String password, String name, String email) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public static User createUser(Map<String, String> userInfo) {
        List<String> fieldNames = Arrays.stream(User.class.getDeclaredFields())
                .map(Field::getName)
                .collect(toList());
        fieldNames.forEach(field -> {
            if (!userInfo.containsKey(field)) {
                throw new UserCreateException("잘못된 User 생성입니다.");
            }
        });

        return new User(userInfo.get(fieldNames.get(0)), userInfo.get(fieldNames.get(1)),
                userInfo.get(fieldNames.get(2)), userInfo.get(fieldNames.get(3)));
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
