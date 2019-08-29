package com.woowacourse.dsgram.service.dto.follow;

import com.woowacourse.dsgram.domain.FileInfo;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Setter
@ToString
public class FollowInfo {
    private String nickName;
    private String userName;
    private FileInfo fileInfo;

    public FollowInfo(String nickName, String userName, FileInfo fileInfo) {
        this.nickName = nickName;
        this.userName = userName;
        this.fileInfo = fileInfo;
    }
}
