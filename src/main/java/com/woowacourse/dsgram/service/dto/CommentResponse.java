package com.woowacourse.dsgram.service.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
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