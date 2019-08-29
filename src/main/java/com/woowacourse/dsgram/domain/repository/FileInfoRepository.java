package com.woowacourse.dsgram.domain.repository;

import com.woowacourse.dsgram.domain.FileInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileInfoRepository extends JpaRepository<FileInfo, Long> {
}
