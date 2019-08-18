package com.woowacourse.dsgram.service.vo;

public class FileInfo {
    private String fileName;
    private String filePath;

    public FileInfo(String fileName, String filePath) {
        this.fileName = fileName;
        this.filePath = filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFilePath() {
        return filePath;
    }
}
