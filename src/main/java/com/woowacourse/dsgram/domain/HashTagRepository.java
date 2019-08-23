package com.woowacourse.dsgram.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HashTagRepository extends JpaRepository<HashTag, Long> {
    // TODO: 2019-08-21 Query 보강 (hashTag 많은 순으로?)
    Page<HashTag> findAllDistinctByKeywordIgnoreCaseContaining(String keyword, Pageable pageable);
}
