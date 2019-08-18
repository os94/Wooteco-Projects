package com.woowacourse.dsgram.domain;


import lombok.Getter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
public class Article {

    @Id
    @Column(name = "article_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(nullable = false)
    private String contents;

    @Column(nullable = false, length = 240)
    private String fileName;

    @Column(nullable = false)
    private String filePath;

    public Article() {
    }

    public Article(String contents, String fileName, String filePath) {
        this.contents = contents;
        this.fileName = fileName;
        this.filePath = filePath;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", contents='" + contents + '\'' +
                ", fileName='" + fileName + '\'' +
                ", filePath='" + filePath + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        return Objects.equals(id, article.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, contents, fileName, filePath);
    }
}
