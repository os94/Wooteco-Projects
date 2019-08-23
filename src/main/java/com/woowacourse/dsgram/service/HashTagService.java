package com.woowacourse.dsgram.service;

import com.woowacourse.dsgram.domain.HashTag;
import com.woowacourse.dsgram.domain.HashTagRepository;
import com.woowacourse.dsgram.service.dto.HashTagResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HashTagService {
    private final HashTagRepository hashTagRepository;

    public HashTagService(HashTagRepository hashTagRepository) {
        this.hashTagRepository = hashTagRepository;
    }

    public void saveHashTags(List<HashTag> hashTags) {
        // TODO: 2019-08-21 id 1번은 어디에 저장되는가...
        hashTagRepository.saveAll(hashTags);
    }

    public HashTagResponse findAll(String query) {
        Page<HashTag> hashTags = hashTagRepository.findAllDistinctByKeywordIgnoreCaseContaining(query,
                PageRequest.of(0, 10, Sort.by("id").descending()));
        return new HashTagResponse(hashTags.getContent());
    }
}
