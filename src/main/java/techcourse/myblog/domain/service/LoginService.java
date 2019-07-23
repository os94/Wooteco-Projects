package techcourse.myblog.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import techcourse.myblog.domain.User;
import techcourse.myblog.domain.exception.MisMatchPasswordException;
import techcourse.myblog.domain.exception.UserNotFoundException;
import techcourse.myblog.domain.repository.UserRepository;

@Service
public class LoginService {
    private final UserRepository userRepository;

    @Autowired
    public LoginService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User login(String email, String password) {
        if (notExistUserEmail(email)) {
            throw new UserNotFoundException("해당 Email의 사용자를 찾을 수 없습니다.");
        }
        if (!matchEmailAndPassword(email, password)) {
            throw new MisMatchPasswordException("비밀번호가 일치하지 않습니다.");
        }
        return userRepository.findUserByEmail(email);
    }

    public boolean isDuplicateEmail(String email) {
        return userRepository.findUserByEmail(email) != null;
    }

    public boolean notExistUserEmail(String email) {
        return userRepository.findUserByEmail(email) == null;
    }

    public boolean matchEmailAndPassword(String email, String password) {
        return userRepository.findUserByEmail(email).matchPassword(password);
    }
}