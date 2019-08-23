package com.woowacourse.dsgram.domain;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode(of = "id")
public class HashTag {
    @Id
    @GeneratedValue
    private long id;

    @Column
    private String keyword;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_hashTag_article"))
    private Article article;

    public HashTag(String keyword, Article article) {
        this.keyword = keyword;
        this.article = article;
    }
}
