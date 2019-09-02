package com.woowacourse.dsgram.domain.repository;

import com.woowacourse.dsgram.domain.HashTag;
import com.woowacourse.dsgram.domain.HashTagSearchResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.woowacourse.dsgram.domain.repository.query.HashTagQuery.FIND_ALL_BY_QUERY;

@Repository
public interface HashTagRepository extends JpaRepository<HashTag, Long> {
    void deleteAllByArticleId(long articleId);

    @Query(FIND_ALL_BY_QUERY)
    List<HashTagSearchResult> findResult(@Param("query") String query, Pageable pageable);

    Page<HashTag> findAllByKeywordContainingOrderByCreatedDate(Pageable pageable, String keyword);
}
