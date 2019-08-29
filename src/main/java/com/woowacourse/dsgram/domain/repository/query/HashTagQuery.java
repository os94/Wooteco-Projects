package com.woowacourse.dsgram.domain.repository.query;

public class HashTagQuery {
    public static final String FIND_ALL_BY_QUERY =
            "SELECT new com.woowacourse.dsgram.domain.HashTagSearchResult(h.keyword, count(h)) " +
                    "FROM " +
                    "HashTag h " +
                    "GROUP BY h.keyword " +
                    "HAVING h.keyword like %:query% ORDER BY 2 DESC";
}
