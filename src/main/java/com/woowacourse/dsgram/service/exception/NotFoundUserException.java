package com.woowacourse.dsgram.service.exception;

public class NotFoundUserException extends RuntimeException {
    public NotFoundUserException() {
        super("회원을 찾을 수 없습니다.");
    }
}
