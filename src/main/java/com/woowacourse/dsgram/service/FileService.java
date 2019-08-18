package com.woowacourse.dsgram.service;

import com.woowacourse.dsgram.domain.Article;
import com.woowacourse.dsgram.service.exception.FileUploadException;
import com.woowacourse.dsgram.service.exception.NotFoundFileException;
import com.woowacourse.dsgram.service.vo.FileInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;
import java.util.UUID;

@Service
public class FileService {

    @Value("${article.upload.path}")
    private String articleUploadPath;

    @Value("${tomcat.path}")
    private String tomcatPath;

    @Value("${BASE_DIR}")
    private String baseDir;

    public FileInfo save(MultipartFile multiFile) {
        String fileName = UUID.randomUUID().toString() + multiFile.getOriginalFilename();
        String filePath = baseDir + tomcatPath + articleUploadPath;

        makeDirectory(filePath);
        saveFile(multiFile, fileName, filePath);

        return new FileInfo(fileName, filePath);
    }

    private void makeDirectory(String filePath) {
        File directory = new File(filePath);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    private void saveFile(MultipartFile multiFile, String fileName, String filePath) {
        File file = new File(filePath, fileName);
        try {
            multiFile.transferTo(file);
        } catch (IOException e) {
            throw new FileUploadException("파일저장경로를 찾지못했습니다.", e);
        }
    }

    public byte[] readFile(Article article) {
        File file = new File(article.getFilePath() + "/" + article.getFileName());

        try {
            byte[] bytes = Files.readAllBytes(file.toPath());
            return Base64.getEncoder().encode(bytes);
        } catch (IOException e) {
            throw new NotFoundFileException("파일을 찾지 못했습니다.", e);
        }

    }
}
