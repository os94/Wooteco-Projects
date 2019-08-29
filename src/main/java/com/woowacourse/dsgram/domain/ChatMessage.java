package com.woowacourse.dsgram.domain;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@EqualsAndHashCode(of = {"id"})
public class ChatMessage extends BaseEntity {
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

    @Override
    public String toString() {
        return "ChatMessage{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", from=" + from +
                ", chatRoom=" + chatRoom +
                '}';
    }
}
