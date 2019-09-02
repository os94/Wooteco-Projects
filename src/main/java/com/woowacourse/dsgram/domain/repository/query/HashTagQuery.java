package com.woowacourse.dsgram.domain.repository.query;

public class HashTagQuery {
    public static final String FIND_ALL_BY_QUERY =
            "SELECT h.keyword AS keyword, COUNT(h) AS count " +
                    "FROM HashTag h " +
                    "GROUP BY h.keyword " +
                    "HAVING h.keyword like %:query% " +
                    "ORDER BY 2 DESC";
}
