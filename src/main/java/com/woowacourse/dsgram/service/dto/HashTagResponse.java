package com.woowacourse.dsgram.service.dto;

import com.woowacourse.dsgram.domain.vo.HashTagSearchResult;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HashTagResponse {
    private List<HashTagSearchResult> hashTags;

    public HashTagResponse(List<HashTagSearchResult> hashTags) {
        this.hashTags = hashTags;
    }
}
