package com.woowacourse.dsgram.service;

import com.woowacourse.dsgram.domain.Article;
import com.woowacourse.dsgram.domain.HashTag;
import com.woowacourse.dsgram.domain.repository.HashTagRepository;
import com.woowacourse.dsgram.service.assembler.ArticleAssembler;
import com.woowacourse.dsgram.service.dto.HashTagResponse;
import com.woowacourse.dsgram.service.dto.article.ArticleInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.woowacourse.dsgram.domain.HashTagSearchResult.LIMIT;
import static com.woowacourse.dsgram.domain.HashTagSearchResult.START_PAGE;
import static java.util.stream.Collectors.toList;

@Service
@Transactional
public class HashTagService {
    private final HashTagRepository hashTagRepository;

    public HashTagService(HashTagRepository hashTagRepository) {
        this.hashTagRepository = hashTagRepository;
    }

    public void saveHashTags(Article article) {
        hashTagRepository.saveAll(extractHashTags(article));
    }

    private List<HashTag> extractHashTags(Article article) {
        return article.getKeyword().stream()
                .map(keyword -> new HashTag(keyword, article))
                .collect(toList());
    }

    @Transactional(readOnly = true)
    public HashTagResponse findAllWithCountByQuery(String query) {
        return new HashTagResponse(hashTagRepository.findResult(query, PageRequest.of(START_PAGE, LIMIT)));
    }

    private void deleteAllByArticleId(long articleId) {
        hashTagRepository.deleteAllByArticleId(articleId);
    }

    public void update(Article article) {
        deleteAllByArticleId(article.getId());
        saveHashTags(article);
    }

    @Transactional(readOnly = true)
    public Page<ArticleInfo> findAllByKeyword(String keyword, int page) {
        return hashTagRepository.findAllByKeywordContainingOrderByCreatedDate(PageRequest.of(page, 10), keyword)
                .map(HashTag::getArticle)
                .map(ArticleAssembler::toArticleInfo);
    }
}
