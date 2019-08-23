package com.woowacourse.dsgram.service.strategy;

public interface FileNamingStrategy {
    String makeUniquePrefix(String originalFilename);

    String makePath();
}
