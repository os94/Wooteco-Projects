package com.woowacourse.dsgram.service.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Setter
public class ArticleEditRequest {
    private String contents;

    public ArticleEditRequest(String contents) {
        this.contents = contents;
    }

    @Override
    public String toString() {
        return "ArticleEditRequest{" +
                "contents='" + contents + '\'' +
                '}';
    }
}
