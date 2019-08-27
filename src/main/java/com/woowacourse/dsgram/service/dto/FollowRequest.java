package com.woowacourse.dsgram.service.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FollowRequest {
    private String fromNickName;
    private String toNickName;

    @Builder
    public FollowRequest(String fromNickName, String toNickName) {
        this.fromNickName = fromNickName;
        this.toNickName = toNickName;
    }
}
