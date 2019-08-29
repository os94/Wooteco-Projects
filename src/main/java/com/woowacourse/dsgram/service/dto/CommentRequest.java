package com.woowacourse.dsgram.service.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ToString
public class CommentRequest {
    private String contents;
    private Long articleId;

    public CommentRequest(String contents, Long articleId) {
        this.contents = contents;
        this.articleId = articleId;
    }
}