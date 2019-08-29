package com.woowacourse.dsgram.service.exception;

public class NotFoundCommentException extends RuntimeException {
    public NotFoundCommentException() {
        super("댓글을 찾을 수 없습니다.");
    }
}