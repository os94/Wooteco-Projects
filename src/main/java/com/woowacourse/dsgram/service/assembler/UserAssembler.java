package com.woowacourse.dsgram.service.assembler;

import com.woowacourse.dsgram.domain.FileInfo;
import com.woowacourse.dsgram.domain.User;
import com.woowacourse.dsgram.service.dto.oauth.OAuthUserInfoResponse;
import com.woowacourse.dsgram.service.dto.user.EditUserRequest;
import com.woowacourse.dsgram.service.dto.user.LoggedInUser;
import com.woowacourse.dsgram.service.dto.user.SignUpUserRequest;
import com.woowacourse.dsgram.service.dto.user.UserDto;

import java.util.Optional;

public class UserAssembler {
    public static User toEntity(SignUpUserRequest signUpUserRequest) {
        return User.builder()
                .email(signUpUserRequest.getEmail())
                .nickName(signUpUserRequest.getNickName())
                .password(signUpUserRequest.getPassword())
                .userName(signUpUserRequest.getUserName())
                .intro("")
                .webSite("")
                .build();
    }

    public static User toEntity(String email, OAuthUserInfoResponse userInfo) {
        return User.builder()
                .email(email)
                .nickName(String.valueOf(email.hashCode()))
                .password(String.valueOf(email.hashCode()))
                .webSite(userInfo.getHtml_url())
                .userName(ifBlankName(userInfo.getName()))
                .isOauthUser(true)
                .build();
    }

    private static String ifBlankName(String name) {
        return Optional.ofNullable(name).orElse("이름을 바꿔주세요.");
    }

    public static UserDto toDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .nickName(user.getNickName())
                .userName(user.getUserName())
                .password(user.getPassword())
                .intro(user.getIntro())
                .webSite(user.getWebSite())
                .build();
    }

    public static LoggedInUser toLoginUser(User user) {
        return LoggedInUser.builder()
                .id(user.getId())
                .nickName(user.getNickName())
                .userName(user.getUserName())
                .email(user.getEmail())
                .build();
    }

    public static User toEntity(EditUserRequest editUserRequest, FileInfo fileInfo) {
        return User.builder()
                .nickName(editUserRequest.getNickName())
                .userName(editUserRequest.getUserName())
                .password(editUserRequest.getPassword())
                .intro(editUserRequest.getIntro())
                .webSite(editUserRequest.getWebSite())
                .fileInfo(fileInfo)
                .build();
    }
}
