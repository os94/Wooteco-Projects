package com.woowacourse.dsgram.service.dto.follow;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Setter
@ToString
public class FollowRequest {
    private String fromNickName;
    private String toNickName;

    @Builder
    public FollowRequest(String fromNickName, String toNickName) {
        this.fromNickName = fromNickName;
        this.toNickName = toNickName;
    }
}
