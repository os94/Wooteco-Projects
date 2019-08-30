package com.woowacourse.dsgram.service.dto.article;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ToString
public class ArticleInfo {

    private Long userId;
    private String nickName;
    private Long articleId;
    private String contents;
    private String articleFileName;
    private Long countOfLikes;
    private Long countOfComments;
    private boolean like;

    @Builder
    public ArticleInfo(Long userId, String nickName, Long articleId, String contents,
                       String articleFileName, Long countOfLikes, Long countOfComments, boolean like) {
        this.userId = userId;
        this.nickName = nickName;
        this.articleId = articleId;
        this.contents = contents;
        this.articleFileName = articleFileName;
        this.countOfLikes = countOfLikes;
        this.countOfComments = countOfComments;
        this.like = like;
    }
}