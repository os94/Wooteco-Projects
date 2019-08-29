package com.woowacourse.dsgram.service.dto.chat;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ToString
public class ChatMessageRequest {
    private long from;
    private String content;

    public ChatMessageRequest(long from, String content) {
        this.from = from;
        this.content = content;
    }
}
