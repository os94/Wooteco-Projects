package com.woowacourse.dsgram.service.dto.chat;

import com.woowacourse.dsgram.service.dto.user.UserDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ToString
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
