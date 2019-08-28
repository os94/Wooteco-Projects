package com.woowacourse.dsgram.service.exception;

public class NotFoundChatRoomException extends RuntimeException {
    public NotFoundChatRoomException(String message) {
        super(message);
    }
}
