package com.woowacourse.dsgram.service.dto.article;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Setter
@ToString
public class ArticleRequest {

    private String contents;
    private MultipartFile file;

    public ArticleRequest(String contents, MultipartFile file) {
        this.contents = contents;
        this.file = file;
    }
}
