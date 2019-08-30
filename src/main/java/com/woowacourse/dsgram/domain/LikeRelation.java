package com.woowacourse.dsgram.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(of = {"id"})
public class LikeRelation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @ManyToOne
    private Article article;

    @ManyToOne
    @JoinColumn(name = "likeUser")
    private User user;

    @Builder
    public LikeRelation(Article article, User user) {
        this.article = article;
        this.user = user;
    }
}