package techcourse.myblog.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import techcourse.myblog.domain.exception.DuplicateEmailException;
import techcourse.myblog.domain.exception.MisMatchPasswordException;
import techcourse.myblog.domain.exception.MisMatchUserException;
import techcourse.myblog.domain.exception.UserNotFoundException;
import techcourse.myblog.domain.model.User;
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

    @Transactional(readOnly = true)
    public List<User> findAll() {
        return unmodifiableList(userRepository.findAll());
    }

    @Transactional(readOnly = true)
    public User findById(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("사용자를 찾지 못했습니다."));
    }

    @Transactional(readOnly = true)
    public User findByIdAsOwner(long id, User loginUser) {
        User user = findById(id);
        if (!user.isSamePerson(loginUser)) {
            throw new MisMatchUserException("본인만 접근가능합니다.");
        }
        return user;
    }

    @Transactional
    public User save(User user) {
        userRepository.findByEmail(user.getEmail()).ifPresent(
                other -> {
                    throw new DuplicateEmailException("중복된 Email입니다.");
                });
        return userRepository.save(user);
    }

    @Transactional
    public User updateByIdAsOwner(long id, MyPageRequestDto userInfo, User loginUser) {
        User user = findById(id);
        if (!user.isSamePerson(loginUser)) {
            throw new MisMatchUserException("본인만 접근가능합니다.");
        }
        return user.update(userInfo.getName());
    }

    @Transactional
    public void deleteByIdAsOwner(long id, User loginUser) {
        if (!findById(id).isSamePerson(loginUser)) {
            throw new MisMatchUserException("본인만 접근가능합니다.");
        }
        userRepository.deleteById(id);
    }

    @Transactional
    public User login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("해당 Email의 사용자를 찾을 수 없습니다."));
        if (!user.matchPassword(password)) {
            throw new MisMatchPasswordException("비밀번호가 일치하지 않습니다.");
        }
        return user;
    }
}
