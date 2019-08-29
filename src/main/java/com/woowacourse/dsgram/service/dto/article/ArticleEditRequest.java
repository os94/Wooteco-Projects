package com.woowacourse.dsgram.service.dto.article;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Setter
@ToString
public class ArticleEditRequest {
    private String contents;

    public ArticleEditRequest(String contents) {
        this.contents = contents;
    }
}
