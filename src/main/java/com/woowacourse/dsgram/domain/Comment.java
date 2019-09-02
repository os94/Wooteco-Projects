package com.woowacourse.dsgram.domain;

import com.woowacourse.dsgram.domain.exception.InvalidUserException;
import com.woowacourse.dsgram.service.dto.CommentRequest;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = {"id"}, callSuper = false)
public class Comment extends BaseEntity {

    @Id
    @Column(name = "comment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "article_id", foreignKey = @ForeignKey(name = "fk_comment_to_article"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Article article;

    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_comment_to_user"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @Column(nullable = false)
    @Lob
    private String contents;

    @Builder
    public Comment(Article article, User user, String contents) {
        this.article = article;
        this.user = user;
        this.contents = contents;
    }

    public void checkAccessibleAuthor(long editUserId) {
        if (user.isNotSameUser(editUserId)) {
            throw new InvalidUserException("글 작성자만 수정, 삭제가 가능합니다.");
        }
    }

    public void update(CommentRequest commentRequest) {
        this.contents = commentRequest.getContents();
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", article=" + article +
                ", user=" + user +
                ", contents='" + contents + '\'' +
                '}';
    }
}