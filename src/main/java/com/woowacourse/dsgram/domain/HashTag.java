package com.woowacourse.dsgram.domain;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@EqualsAndHashCode(of = "id")
public class HashTag extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String keyword;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_hashTag_article"))
    private Article article;

    @Builder
    public HashTag(String keyword, Article article) {
        this.keyword = keyword;
        this.article = article;
    }

    @Override
    public String toString() {
        return "HashTag{" +
                "id=" + id +
                ", keyword='" + keyword + '\'' +
                ", article=" + article +
                '}';
    }
}
