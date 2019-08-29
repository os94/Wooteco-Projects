package com.woowacourse.dsgram.service.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ToString
public class CommentResponse {
    private Long articleId;
    private Long commentId;
    private String contents;

    public CommentResponse(Long articleId, Long commentId, String contents) {
        this.articleId = articleId;
        this.commentId = commentId;
        this.contents = contents;
    }
}