package com.woowacourse.dsgram.service.exception;

public class EmptyCommentRequestException extends RuntimeException {
    public EmptyCommentRequestException(String message) {
        super(message);
    }
}