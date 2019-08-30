package com.woowacourse.dsgram.service.dto;

import lombok.Getter;

@Getter
public class LikeRequest {

    private boolean likeState;
    private long articleId;

    public LikeRequest(boolean likeState, long articleId) {
        this.likeState = likeState;
        this.articleId = articleId;
    }
}
