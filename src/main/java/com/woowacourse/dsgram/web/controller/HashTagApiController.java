package com.woowacourse.dsgram.web.controller;

import com.woowacourse.dsgram.service.HashTagService;
import com.woowacourse.dsgram.service.dto.HashTagResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/hashTags")
public class HashTagApiController {
    private final HashTagService hashTagService;

    public HashTagApiController(HashTagService hashTagService) {
        this.hashTagService = hashTagService;
    }

    @GetMapping
    public ResponseEntity<HashTagResponse> searchHashTag(String query) {
        return ResponseEntity.ok(hashTagService.findAllWithCountByQuery(query));
    }

    @GetMapping("/{keyword}")
    public ResponseEntity searchArticleByHashTag(@PathVariable String keyword,
                                                 @RequestParam(defaultValue = "0") int page) {
        return ResponseEntity.ok(hashTagService.findAllByKeyword(keyword, page));
    }
}
