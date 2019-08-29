package com.woowacourse.dsgram.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HashTagSearchResult {
    private String keyword;
    private long count;

    public HashTagSearchResult(String keyword, long count) {
        this.keyword = keyword;
        this.count = count;
    }

    @Override
    public String toString() {
        return "HashTagSearchResult{" +
                "keyword='" + keyword + '\'' +
                ", count=" + count +
                '}';
    }
}
