package techcourse.myblog.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import techcourse.myblog.domain.UserRepository;

@Service
public class LoginService {
    private final UserRepository userRepository;

    @Autowired
    public LoginService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean isDuplicateEmail(String email) {
        return userRepository.findUsersByEmail(email).size() != 0;
    }

    public boolean notExistUserEmail(String email) {
        return userRepository.findUserByEmail(email) == null;
    }

    public boolean matchEmailAndPassword(String email, String password) {
        return userRepository.findUserByEmail(email).matchPassword(password);
    }
}
