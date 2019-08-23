package com.woowacourse.dsgram.service;

import com.woowacourse.dsgram.domain.FileInfo;
import com.woowacourse.dsgram.domain.repository.FileInfoRepository;
import com.woowacourse.dsgram.service.exception.FileUploadException;
import com.woowacourse.dsgram.service.exception.NotFoundFileException;
import com.woowacourse.dsgram.service.strategy.FileNamingStrategy;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

@Service
public class FileService {

    private final FileInfoRepository fileInfoRepository;

    public FileService(FileInfoRepository fileInfoRepository) {
        this.fileInfoRepository = fileInfoRepository;
    }

    public FileInfo save(MultipartFile multiFile, FileNamingStrategy strategy) {
        String fileName = strategy.makeUniquePrefix(multiFile.getOriginalFilename());
        String filePath = strategy.makePath();

        makeDirectory(filePath);
        saveFile(multiFile, fileName, filePath);

        return fileInfoRepository.save(new FileInfo(fileName, filePath));
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

    public byte[] readFileByFileInfo(FileInfo fileInfo) {
        File file = new File(fileInfo.getFilePath() + "/" + fileInfo.getFileName());
        try {
            byte[] bytes = Files.readAllBytes(file.toPath());
            return Base64.getEncoder().encode(bytes);
        } catch (IOException e) {
            throw new NotFoundFileException("파일을 찾지 못했습니다.", e);
        }
    }
}
