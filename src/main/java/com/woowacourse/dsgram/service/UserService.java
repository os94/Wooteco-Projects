package com.woowacourse.dsgram.service;

import com.google.gson.JsonElement;
import com.woowacourse.dsgram.domain.User;
import com.woowacourse.dsgram.domain.UserRepository;
import com.woowacourse.dsgram.domain.exception.InvalidUserException;
import com.woowacourse.dsgram.service.assembler.UserAssembler;
import com.woowacourse.dsgram.service.dto.user.AuthUserRequest;
import com.woowacourse.dsgram.service.dto.user.LoginUserRequest;
import com.woowacourse.dsgram.service.dto.user.SignUpUserRequest;
import com.woowacourse.dsgram.service.dto.user.UserDto;
import com.woowacourse.dsgram.service.exception.DuplicatedAttributeException;
import com.woowacourse.dsgram.service.exception.NotFoundUserException;
import com.woowacourse.dsgram.service.oauth.GithubClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final GithubClient githubClient;

    public UserService(UserRepository userRepository, GithubClient githubClient) {
        this.userRepository = userRepository;
        this.githubClient = githubClient;
    }

    public void save(SignUpUserRequest signUpUserRequest) {
        checkDuplicatedAttributes(signUpUserRequest.getNickName(), signUpUserRequest.getEmail());
        userRepository.save(UserAssembler.toEntity(signUpUserRequest));
    }

    private void checkDuplicatedAttributes(String nickName, String email) {
        if (userRepository.existsByNickName(nickName)) {
            throw new DuplicatedAttributeException("이미 사용중인 닉네임입니다.");
        }
        if (userRepository.existsByEmail(email)) {
            throw new DuplicatedAttributeException("이미 사용중인 이메일입니다.");
        }
    }

    public UserDto findUserInfoById(long userId, LoginUserRequest loginUserRequest) {
        User user = findById(userId);
        user.checkEmail(loginUserRequest.getEmail());
        return UserAssembler.toDto(findById(userId));
    }

    private User findById(long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundUserException("회원을 찾을 수 없습니다."));
    }

    @Transactional
    public void update(long userId, UserDto userDto, LoginUserRequest loginUserRequest) {
        User user = findById(userId);
        checkDuplicatedNickName(userDto, user);
        user.update(UserAssembler.toEntity(userDto), loginUserRequest.getEmail());
    }

    private void checkDuplicatedNickName(UserDto userDto, User user) {
        if (!user.equalsNickName(userDto.getNickName()) &&
                userRepository.existsByNickName(userDto.getNickName())) {
            throw new DuplicatedAttributeException("이미 사용중인 닉네임입니다.");
        }
    }

    public LoginUserRequest login(AuthUserRequest authUserRequest) {
        User user = userRepository.findByEmail(authUserRequest.getEmail())
                .orElseThrow(() -> new InvalidUserException("회원정보가 일치하지 않습니다."));
        user.checkPassword(authUserRequest.getPassword());
        return UserAssembler.toAuthUserDto(user);
    }

    private Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public LoginUserRequest oauth(String code) {
        String accessToken = githubClient.getToken(code);

        String email = githubClient.getUserEmail(accessToken);

        Optional<User> user = findByEmail(email);
        if (user.isPresent()) {
            return UserAssembler.toAuthUserDto(user.get());
        }
        return UserAssembler.toAuthUserDto(saveOauthUser(accessToken, email));
    }

    private User saveOauthUser(String accessToken, String email) {
        // TODO: 2019-08-16 OAUTH 로그인시 nickName null/중복 처리
        JsonElement userInfo = githubClient.getUserInformation(accessToken);
        return userRepository.save(UserAssembler.toEntity(email, userInfo.getAsJsonObject()));
    }
}
