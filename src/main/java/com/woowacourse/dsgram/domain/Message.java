package com.woowacourse.dsgram.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Lob
    @Column(nullable = false)
    private String content;

    @ManyToOne
    @Column(nullable = false)
    private User from;

    @ManyToOne
    @Column(nullable = false)
    private ChatRoom chatRoom;
}
