package com.woowacourse.dsgram.service.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ChatMessageRequest {
    private long from;
    private String content;

    public ChatMessageRequest(long from, String content) {
        this.from = from;
        this.content = content;
    }
}
