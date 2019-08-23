package com.woowacourse.dsgram.service.dto;

import com.woowacourse.dsgram.domain.HashTag;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class HashTagResponse {
    private List<HashTag> hashTags;

    public HashTagResponse(List<HashTag> hashTags) {
        this.hashTags = hashTags;
    }
}
