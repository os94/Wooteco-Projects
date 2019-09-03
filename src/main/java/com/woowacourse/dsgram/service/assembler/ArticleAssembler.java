package com.woowacourse.dsgram.service.assembler;

import com.woowacourse.dsgram.domain.Article;
import com.woowacourse.dsgram.domain.User;
import com.woowacourse.dsgram.service.dto.article.ArticleInfo;

public class ArticleAssembler {
    public static ArticleInfo toArticleInfo(Article article) {
        User author = article.getAuthor();
        return ArticleInfo.builder()
                .articleId(article.getId())
                .articleFileName(article.getFileInfo().getFileName())
                .contents(article.getContents())
                .nickName(author.getNickName())
                .userId(author.getId())
                .build();
    }
}