package com.woowacourse.dsgram.service;

import com.woowacourse.dsgram.domain.User;
import com.woowacourse.dsgram.domain.exception.InvalidUserException;
import com.woowacourse.dsgram.domain.repository.UserRepository;
import com.woowacourse.dsgram.service.dto.oauth.OAuthUserInfoResponse;
import com.woowacourse.dsgram.service.dto.user.*;
import com.woowacourse.dsgram.service.exception.DuplicatedAttributeException;
import com.woowacourse.dsgram.service.exception.NotFoundUserException;
import com.woowacourse.dsgram.service.oauth.GithubClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@ExtendWith(SpringExtension.class)
class UserServiceTest {
    private final SignUpUserRequest signUpUserRequest = SignUpUserRequest.builder()
            .email("buddy@buddy.com")
            .userName("김버디")
            .nickName("buddy_")
            .password("Aa12345!")
            .build();

    private final User user = User.builder()
            .email("buddy@buddy.com")
            .userName("김버디")
            .nickName("buddy_")
            .password("Aa12345!")
            .build();

    private OAuthUserInfoResponse userInfo = OAuthUserInfoResponse.builder()
            .email(user.getEmail())
            .login(user.getNickName())
            .name(user.getUserName())
            .build();

    private final AuthUserRequest authUserRequest = new AuthUserRequest("buddy@buddy.com", "Aa12345!");

    private final EditUserRequest editUserRequest = EditUserRequest.builder()
            .userName("qwe")
            .file(Optional.empty())
            .intro("qwe")
            .nickName("qwe")
            .password("qweqwe")
            .webSite("qwe")
            .build();

    private final LoggedInUser loggedInUser = new LoggedInUser(1L, "buddy@buddy.com", "buddy_", "김버디");

    private final String code = "af70401e9c65f83fe29e";
    private final String sampleToken = "mq4m38cvt3ucw498ub2er";
    private final String email = "buddy@buddy.com";


    @InjectMocks
    private UserService userService;

    @Mock
    UserRepository userRepository;

    @Mock
    private GithubClient githubClient;

    @Test
    void 유저_저장_성공() {
        given(userRepository.save(user)).willReturn(user);

        userService.save(signUpUserRequest);

        verify(userRepository, times(1)).save(any());
    }

    @Test
    void 유저_저장_실패_닉네임_중복() {
        given(userRepository.existsByNickName(any())).willReturn(true);

        assertThrows(RuntimeException.class, () -> {
            userService.save(signUpUserRequest);
        });
    }

    @Test
    void 유저_저장_실패_이메일_중복() {
        given(userRepository.existsByNickName(anyString())).willReturn(false);
        given(userRepository.existsByEmail(anyString())).willReturn(true);

        assertThrows(DuplicatedAttributeException.class, () -> userService.save(signUpUserRequest));
    }

    @Test
    void 로그인_성공() {
        given(userRepository.findByEmail(any())).willReturn(Optional.of(user));

        userService.login(authUserRequest);

        verify(userRepository, times(1)).findByEmail(any());
    }

    @Test
    void 로그인_실패_이메일_존재안함() {
        given(userRepository.findByEmail(any())).willReturn(Optional.empty());

        assertThrows(InvalidUserException.class, () -> userService.login(authUserRequest));
    }

    @Test
    void 로그인_실패_패스워드_불일치() {
        given(userRepository.findByEmail(any())).willReturn(Optional.of(user));

        assertThrows(InvalidUserException.class, () -> userService.login(new AuthUserRequest("buddy@buddy.com", "exception")));
    }

    @Test
    void 유저_정보_수정_성공() {
        given(userRepository.findById(1L)).willReturn(Optional.of(user));

        UserDto userDto = new UserDto(1L, "김버디", "buddy_2", "Aa12345!", "www.website.com", "intro");
        LoggedInUser loggedInUser = new LoggedInUser(1L, "buddy@buddy.com", "buddy_", "김버디");
        assertDoesNotThrow(() -> userService.update(1L, editUserRequest, loggedInUser));
    }

    @Test
    void 유저_정보_실패_없는_유저() {
        given(userRepository.findById(anyLong())).willReturn(Optional.empty());

        assertThrows(NotFoundUserException.class, () -> userService.update(anyLong(), editUserRequest, loggedInUser));
    }

    @Test
    void 남의_정보를_수정() {
        given(userRepository.findById(anyLong())).willReturn(Optional.of(user));
        given(userRepository.existsByNickName(anyString())).willReturn(false);
        assertThrows(InvalidUserException.class, () -> userService.update(anyLong(), editUserRequest, new LoggedInUser(1L, "user@gmail", "user", "user")));
    }

    @Test
    void 유저_수정_실패_닉네임_중복() {
        given(userRepository.findById(any())).willReturn(Optional.of(user));
        given(userRepository.existsByNickName(any())).willReturn(true);

        assertThrows(DuplicatedAttributeException.class, () -> userService.update(anyLong(), editUserRequest, loggedInUser));
    }

    @Test
    void oauth_firstLogin() {
        given(githubClient.getToken(code)).willReturn(sampleToken);
        given(githubClient.getUserEmail(sampleToken)).willReturn(email);

        given(userRepository.findByEmail(email)).willReturn(Optional.empty());

        given(userRepository.save(user)).willReturn(user);
        given(githubClient.getUserInformation(sampleToken)).willReturn(userInfo);

        LoggedInUser loggedInUser = userService.oauth(code);

        verify(userRepository).save(any());
        assertThat(loggedInUser.getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    void oauth_login() {
        given(githubClient.getToken(code)).willReturn(sampleToken);
        given(githubClient.getUserEmail(sampleToken)).willReturn(email);

        given(userRepository.findByEmail(email)).willReturn(Optional.of(user));

        LoggedInUser loggedInUser = userService.oauth(code);

        assertThat(loggedInUser.getEmail()).isEqualTo(user.getEmail());
    }
}
