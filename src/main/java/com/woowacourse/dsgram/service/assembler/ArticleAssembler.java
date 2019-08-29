package com.woowacourse.dsgram.service.assembler;

import com.woowacourse.dsgram.domain.Article;
import com.woowacourse.dsgram.domain.User;
import com.woowacourse.dsgram.service.dto.ArticleInfo;

public class ArticleAssembler {
    public static ArticleInfo toArticleInfo(Article article, long countOfComments) {
        User author = article.getAuthor();
        return ArticleInfo.builder()
                .articleId(article.getId())
                .articleFileName(article.getFileInfo().getFileName())
                .contents(article.getContents())
                .nickName(author.getNickName())
                .userId(author.getId())
                .countOfLikes(0L)
                .countOfComments(countOfComments)
                .build();
    }

    public static ArticleInfo toArticleInfo(Article article) {
        User author = article.getAuthor();
        return ArticleInfo.builder()
                .articleId(article.getId())
                .articleFileName(article.getFileInfo().getFileName())
                .contents(article.getContents())
                .nickName(author.getNickName())
                .userId(author.getId())
                .countOfLikes(0L)
                .build();
    }
}