package com.woowacourse.dsgram.domain.repository;

import com.woowacourse.dsgram.domain.FileInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileInfoRepository extends JpaRepository<FileInfo, Long> {
}
