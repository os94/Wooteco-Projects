package com.woowacourse.dsgram.service.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Setter
public class ArticleRequest {

    private String contents;
    private MultipartFile file;

    public ArticleRequest(String contents, MultipartFile file) {
        this.contents = contents;
        this.file = file;
    }

    @Override
    public String toString() {
        return "ArticleRequest{" +
                "contents='" + contents + '\'' +
                ", file=" + file +
                '}';
    }
}
