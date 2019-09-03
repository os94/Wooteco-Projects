package com.woowacourse.dsgram.service.dto.user;

import com.woowacourse.dsgram.domain.FileInfo;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Setter
@ToString
public class UserInfo {
    private long id;
    private String nickName;
    private String userName;
    private FileInfo fileInfo;

    public UserInfo(long id, String nickName, String userName, FileInfo fileInfo) {
        this.id = id;
        this.nickName = nickName;
        this.userName = userName;
        this.fileInfo = fileInfo;
    }
}
