package com.woowacourse.dsgram.domain;

import com.sun.tools.javac.util.List;

import javax.persistence.*;

public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long hashCode;

    @OneToMany(mappedBy = "chatRoom")
    private List<Message> messages;

    @OneToOne
    private ChatUsers chatUsers;
}
