package com.woowacourse.dsgram.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@EqualsAndHashCode(of = {"id"})
public class FileInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false)
    private String filePath;

    @Builder
    public FileInfo(String fileName, String filePath) {
        this.fileName = fileName;
        this.filePath = filePath;
    }

    @Override
    public String toString() {
        return "FileInfo{" +
                "id=" + id +
                ", fileName='" + fileName + '\'' +
                ", filePath='" + filePath + '\'' +
                '}';
    }
}
