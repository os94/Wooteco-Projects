package com.woowacourse.dsgram.service.assembler;

import com.woowacourse.dsgram.domain.ChatMessage;
import com.woowacourse.dsgram.service.dto.chat.ChatMessageResponse;
import com.woowacourse.dsgram.service.dto.chat.ChatMessagesRequest;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class ChatAssembler {
    public static ChatMessagesRequest toChatMessagesRequest(List<ChatMessage> chatMessages) {
        return new ChatMessagesRequest(chatMessages.stream()
                .map(message -> new ChatMessageResponse(UserAssembler.toDto(message.getFrom()), message.getContent()))
                .collect(toList()));
    }

    public static ChatMessageResponse toChatMessageResponse(ChatMessage chatMessage) {
        return new ChatMessageResponse(UserAssembler.toDto(chatMessage.getFrom()), chatMessage.getContent());
    }
}
