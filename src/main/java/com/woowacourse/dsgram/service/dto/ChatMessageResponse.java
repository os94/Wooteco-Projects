package com.woowacourse.dsgram.service.dto;

import com.woowacourse.dsgram.service.dto.user.UserDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ChatMessageResponse {

    private UserDto from;
    private String content;

    public ChatMessageResponse(UserDto from, String content) {
        this.from = from;
        this.content = content;
    }

    public long getUserId() {
        return from.getId();
    }
}
