package com.woowacourse.dsgram.service.dto.article;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ToString
public class ArticleInfo {

    private long userId;
    private String nickName;
    private long articleId;
    private String contents;
    private String articleFileName;


    @Builder
    public ArticleInfo(long userId, String nickName, long articleId, String contents, String articleFileName) {
        this.userId = userId;
        this.nickName = nickName;
        this.articleId = articleId;
        this.contents = contents;
        this.articleFileName = articleFileName;
    }
}