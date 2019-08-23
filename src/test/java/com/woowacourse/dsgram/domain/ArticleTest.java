package com.woowacourse.dsgram.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class ArticleTest {

    @Test
    void 글_내용에서_해시태그_추출() {
        User user = User.builder()
                .email("buddy@buddy.com")
                .userName("김버디")
                .nickName("buddy_")
                .password("Aa12345!")
                .build();
        Article article = new Article("#가나다! #11 #11#22 이렇게 쓰다가 갑자기 #exception 터지면?", new FileInfo("sampleName", "samplePath"), user);
        assertThat(article.getKeyword()).contains("#가나다", "#exception", "#11", "#22");
    }
}