package com.woowacourse.dsgram.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(of = {"id"})
public class Follow extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @ManyToOne
    @JoinColumn(name = "follower")
    User from;

    @ManyToOne
    @JoinColumn(name = "followed")
    User to;

    @Builder
    public Follow(User from, User to) {
        this.from = from;
        this.to = to;
    }

}
