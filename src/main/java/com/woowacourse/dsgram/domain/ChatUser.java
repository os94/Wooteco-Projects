package com.woowacourse.dsgram.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
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
}
