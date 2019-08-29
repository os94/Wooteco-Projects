package com.woowacourse.dsgram.service.exception;

public class NotFoundArticleException extends RuntimeException {
    public NotFoundArticleException() {
        super("게시글을 찾을 수 없습니다.");
    }
}