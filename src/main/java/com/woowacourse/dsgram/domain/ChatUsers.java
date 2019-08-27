package com.woowacourse.dsgram.domain;

import com.sun.tools.javac.util.List;

import javax.persistence.*;

public class ChatUsers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    private ChatRoom chatRoom;

    @OneToMany
    private List<User> users;
}
