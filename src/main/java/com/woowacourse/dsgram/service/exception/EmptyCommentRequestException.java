package com.woowacourse.dsgram.service.exception;

public class EmptyCommentRequestException extends RuntimeException{
    public EmptyCommentRequestException() {
        super("댓글의 내용을 입력해주세요.");
    }
}