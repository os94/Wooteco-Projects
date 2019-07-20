package techcourse.myblog.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import techcourse.myblog.domain.User;
import techcourse.myblog.domain.UserRepository;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
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

    public void deleteById(long id) {
        userRepository.deleteById(id);
    }

    public User findUserById(long id) {
        return userRepository.findUserById(id);
    }

    public User updateName(long id, String name) {
        User user = userRepository.findUserById(id);
        user.setName(name);
        return user;
    }

    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }
}
