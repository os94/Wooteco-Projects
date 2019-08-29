package com.woowacourse.dsgram.domain;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@EqualsAndHashCode(of = {"id"})
public class ChatRoom extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true, length = 50, nullable = false)
    private long code;

    @OneToMany(mappedBy = "chatRoom")
    private List<ChatMessage> chatMessages;

    @OneToMany(mappedBy = "chatRoom")
    private List<ChatUser> chatUsers;

    public ChatRoom(long code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "ChatRoom{" +
                "id=" + id +
                ", code=" + code +
                ", chatMessages=" + chatMessages +
                ", chatUsers=" + chatUsers +
                '}';
    }
}
