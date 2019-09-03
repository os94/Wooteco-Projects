package com.woowacourse.dsgram.service.dto;

import lombok.Getter;

@Getter
public class LikeResponse {
    private long countOfLikes;
    private boolean likeState;

    public LikeResponse(long countOfLikes, boolean likeState) {
        this.countOfLikes = countOfLikes;
        this.likeState = likeState;
    }
}
