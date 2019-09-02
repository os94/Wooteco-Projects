package com.woowacourse.dsgram.domain;

public interface HashTagSearchResult {
    int START_PAGE = 0;
    int LIMIT = 5;

    String getKeyword();

    long getCount();
}
