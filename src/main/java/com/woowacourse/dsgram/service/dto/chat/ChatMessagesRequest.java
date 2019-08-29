package com.woowacourse.dsgram.service.dto.chat;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ToString
public class ChatMessagesRequest {
    private List<ChatMessageResponse> prevMessages;

    public ChatMessagesRequest(List<ChatMessageResponse> prevMessages) {
        this.prevMessages = prevMessages;
    }
}
