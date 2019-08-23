package com.woowacourse.dsgram.service.strategy;


import com.woowacourse.dsgram.service.exception.UserDirNullException;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public abstract class CommonFileNamingStrategy implements FileNamingStrategy {

    // TODO : NULL CHECK 어떻게?
    private static final String basePath = System.getProperty("user.dir");
    private static final String FOLDER_DATE_FORMAT = "yyyyMMdd";
    private static final String TIME_ZONE = "Asia/Seoul";
    static final String SEPARATOR = "/";

    @Override
    public String makeUniquePrefix(String originalFilename) {
        return UUID.randomUUID().toString() + originalFilename;
    }

    public String getDate() {
        ZonedDateTime dateTime = ZonedDateTime.now(ZoneId.of(TIME_ZONE));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FOLDER_DATE_FORMAT);
        return dateTime.format(formatter);
    }

    public String getBasePath() {
        if (basePath == null) {
            throw new UserDirNullException("user.dir 이 설정되어 있지 않습니다.");
        }
        return basePath;
    }

}
