package com.woowacourse.dsgram.web.controller;

import com.woowacourse.dsgram.service.HashTagService;
import com.woowacourse.dsgram.service.dto.HashTagResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/hashTag")
public class HashTagApiController {
    private final HashTagService hashTagService;

    public HashTagApiController(HashTagService hashTagService) {
        this.hashTagService = hashTagService;
    }

    private static final Logger log = LoggerFactory.getLogger(HashTagApiController.class);

    @GetMapping
    public ResponseEntity<HashTagResponse> searchHashTag(String query) {
        return ResponseEntity.ok(hashTagService.findAll(query));
    }
}
