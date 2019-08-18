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

    private String hashtag;
    private String contents;
    private MultipartFile file;

    public ArticleRequest(String contents, String hashtag, MultipartFile file) {
        this.contents = contents;
        this.hashtag = hashtag;
        this.file = file;
    }

    @Override
    public String toString() {
        return "ArticleRequest{" +
                "contents='" + contents + '\'' +
                ", hashtag='" + hashtag + '\'' +
                ", file=" + file +
                '}';
    }
}
