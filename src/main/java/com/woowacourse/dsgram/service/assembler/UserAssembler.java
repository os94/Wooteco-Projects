package com.woowacourse.dsgram.service.assembler;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.woowacourse.dsgram.domain.User;
import com.woowacourse.dsgram.service.dto.user.LoginUserRequest;
import com.woowacourse.dsgram.service.dto.user.SignUpUserRequest;
import com.woowacourse.dsgram.service.dto.user.UserDto;

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

    public static User toEntity(UserDto userDto) {
        return User.builder()
                .userName(userDto.getUserName())
                .password(userDto.getPassword())
                .nickName(userDto.getNickName())
                .intro(userDto.getIntro())
                .webSite(userDto.getWebSite())
                .build();
    }

    public static User toEntity(String email, JsonObject userInfo) {
        return User.builder()
                .email(email)
                .nickName(userInfo.get("login").getAsString())
                // TODO: OAUTH로그인시 ID 중복처리 및 Password 설정 필요
                .password("Random password")
                .webSite(userInfo.get("html_url").getAsString())
                .userName(ifBlankName(userInfo))
                .build();
    }

    private static String ifBlankName(JsonObject userInfo) {
        JsonElement name = userInfo.get("name");
        return name.isJsonNull() ? userInfo.get("id").getAsString() : name.getAsString();
    }

    public static UserDto toDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .intro(user.getIntro())
                .nickName(user.getNickName())
                .userName(user.getUserName())
                .password(user.getPassword())
                .webSite(user.getWebSite())
                .build();
    }

    public static LoginUserRequest toAuthUserDto(User user) {
        return LoginUserRequest.builder()
                .nickName(user.getNickName())
                .userName(user.getUserName())
                .email(user.getEmail())
                .build();
    }
}
