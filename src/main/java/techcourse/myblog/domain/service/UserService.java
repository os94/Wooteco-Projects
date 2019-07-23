package techcourse.myblog.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import techcourse.myblog.domain.User;
import techcourse.myblog.domain.exception.DuplicateEmailException;
import techcourse.myblog.domain.repository.UserRepository;
import techcourse.myblog.dto.MyPageRequestDto;

import java.util.List;

import static java.util.Collections.unmodifiableList;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User updateUserInfo(long id, MyPageRequestDto userInfo) {
        User user = userRepository.findUserById(id);
        user.updateUserInfo(userInfo.getName());
        return user;
    }

    public User save(User user) {
        if (userRepository.findUserByEmail(user.getEmail()) != null) {
            throw new DuplicateEmailException("중복된 Email입니다.");
        }
        return userRepository.save(user);
    }

    public List<User> findAll() {
        return unmodifiableList(userRepository.findAll());
    }

    public void deleteById(long id) {
        userRepository.deleteById(id);
    }

    public User findUserById(long id) {
        return userRepository.findUserById(id);
    }

    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }
}
