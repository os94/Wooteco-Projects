package com.woowacourse.dsgram.service.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ChatMessagesRequest {
    private List<ChatMessageResponse> prevMessages;

    public ChatMessagesRequest(List<ChatMessageResponse> prevMessages) {
        this.prevMessages = prevMessages;
    }
}
