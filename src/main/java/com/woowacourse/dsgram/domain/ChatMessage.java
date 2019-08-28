package com.woowacourse.dsgram.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ChatMessage extends DateTimeBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Lob
    @Column(nullable = false)
    private String content;

    @ManyToOne
    private User from;

    @ManyToOne
    private ChatRoom chatRoom;

    public ChatMessage(String content, User from, ChatRoom chatRoom) {
        this.content = content;
        this.from = from;
        this.chatRoom = chatRoom;
    }

    public long getSenderId() {
        return from.getId();
    }
}
