package com.woowacourse.dsgram.service.exception;

public class NotFoundFileException extends RuntimeException {
    public NotFoundFileException(String message, Throwable cause) {
        super(message, cause);
    }
}
