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
public class ChatUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private ChatRoom chatRoom;

    @ManyToOne
    private User users;

    public ChatUser(ChatRoom chatRoom, User users) {
        this.chatRoom = chatRoom;
        this.users = users;
    }

    @Override
    public String toString() {
        return "ChatUser{" +
                "id=" + id +
                ", chatRoom=" + chatRoom +
                ", users=" + users +
                '}';
    }
}
