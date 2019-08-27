package com.woowacourse.dsgram.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(of = {"id"})
public class Follow {

    @Id
    @GeneratedValue // TODO: 2019-08-25 generateType 통일성 없음
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
