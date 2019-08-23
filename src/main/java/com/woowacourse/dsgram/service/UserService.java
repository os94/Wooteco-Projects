package com.woowacourse.dsgram.service;

import com.woowacourse.dsgram.domain.FileInfo;
import com.woowacourse.dsgram.domain.User;
import com.woowacourse.dsgram.domain.exception.InvalidUserException;
import com.woowacourse.dsgram.domain.repository.UserRepository;
import com.woowacourse.dsgram.service.assembler.UserAssembler;
import com.woowacourse.dsgram.service.dto.oauth.OAuthUserInfoResponse;
import com.woowacourse.dsgram.service.dto.user.*;
import com.woowacourse.dsgram.service.exception.DuplicatedAttributeException;
import com.woowacourse.dsgram.service.exception.NotFoundUserException;
import com.woowacourse.dsgram.service.oauth.GithubClient;
import com.woowacourse.dsgram.service.strategy.UserImageFileNamingStrategy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final FileService fileService;
    private final GithubClient githubClient;

    public UserService(UserRepository userRepository, FileService fileService, GithubClient githubClient) {
        this.userRepository = userRepository;
        this.fileService = fileService;
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

    public UserDto findUserInfoById(long userId, LoggedInUser loggedInUser) {
        User user = findUserById(userId);
        user.checkEmail(loggedInUser.getEmail());
        return UserAssembler.toDto(user);
    }

    public User findUserById(long userId) {
        return userRepository.findById(userId)
                .orElseThrow(NotFoundUserException::new);
    }

    @Transactional
    public LoggedInUser update(long userId, EditUserRequest editUserRequest, LoggedInUser loggedInUser) {
        // TODO: 2019-08-22 정리해보기^^;
        User user = findUserById(userId);
        checkDuplicatedNickName(editUserRequest, user);
        Optional<MultipartFile> maybeFile = editUserRequest.getFile();
        FileInfo fileInfo = user.getFileInfo();
        if (maybeFile.isPresent()) {
            fileInfo = fileService.save(maybeFile.get(), new UserImageFileNamingStrategy());
        }
        user.update(UserAssembler.toEntity(editUserRequest, fileInfo), loggedInUser.getEmail());
        return UserAssembler.toLoginUser(user);
    }

    private void checkDuplicatedNickName(EditUserRequest editUserRequest, User user) {
        if (!user.equalsNickName(editUserRequest.getNickName()) &&
                userRepository.existsByNickName(editUserRequest.getNickName())) {
            throw new DuplicatedAttributeException("이미 사용중인 닉네임입니다.");
        }
    }

    public LoggedInUser login(AuthUserRequest authUserRequest) {
        User user = findByEmail(authUserRequest.getEmail())
                .orElseThrow(() -> new InvalidUserException("회원정보가 일치하지 않습니다."));
        user.checkPassword(authUserRequest.getPassword());
        return UserAssembler.toLoginUser(user);
    }

    private Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional
    public LoggedInUser oauth(String code) {
        String accessToken = githubClient.getToken(code);
        String email = githubClient.getUserEmail(accessToken);

        return findByEmail(email).map(maybeUser -> {
            maybeUser.changeToOAuthUser();
            return UserAssembler.toLoginUser(maybeUser);
        }).orElseGet(() -> UserAssembler.toLoginUser(saveOauthUser(accessToken, email)));
    }

    private User saveOauthUser(String accessToken, String email) {
        OAuthUserInfoResponse userInfo = githubClient.getUserInformation(accessToken);
        return userRepository.save(UserAssembler.toEntity(email, userInfo));
    }

    public void deleteUserById(long id, LoggedInUser loggedInUser) {
        // TODO: 2019-08-20 OAUTH revoke?
        User user = findByEmail(loggedInUser.getEmail())
                .orElseThrow(NotFoundUserException::new);
        if (user.isNotSameId(id)) {
            throw new InvalidUserException("회원정보가 일치하지 않습니다.");
        }
        userRepository.deleteById(id);
    }

    public byte[] findProfileImageById(long userId) {
        User user = findUserById(userId);
        FileInfo fileInfo = user.getFileInfo();
        return fileService.readFileByFileInfo(fileInfo);
    }
}
