package com.woowacourse.dsgram.service.strategy;

public class UserImageFileNamingStrategy extends CommonFileNamingStrategy {
    private static final String USER_IMAGE_FILES_PATH = "/users/images";

    @Override
    public String makePath() {
        return getBasePath().concat(SEPARATOR)
                .concat(USER_IMAGE_FILES_PATH)
                .concat(SEPARATOR)
                .concat(getDate());
    }
}
