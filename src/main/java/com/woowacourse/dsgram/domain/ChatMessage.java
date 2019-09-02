package com.woowacourse.dsgram.domain;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@EqualsAndHashCode(of = {"id"})
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ChatMessage {
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

    @CreatedDate
    private LocalDateTime createdDate;

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
                ", createdDate=" + createdDate +
                '}';
    }
}
