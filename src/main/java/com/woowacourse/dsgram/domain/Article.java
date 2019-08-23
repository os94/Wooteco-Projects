package com.woowacourse.dsgram.domain;


import com.woowacourse.dsgram.domain.exception.InvalidUserException;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Entity
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@EqualsAndHashCode(of = {"id"})
public class Article {
    public static final String REGEX = "#([0-9a-zA-Z가-힣_]{2,30})";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Lob
    @Column(nullable = false)
    private String contents;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "FILEINFO_ID")
    private FileInfo fileInfo;

    @ManyToOne
    @JoinColumn(name = "author", foreignKey = @ForeignKey(name = "fk_article_to_user"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User author;

    @Builder
    public Article(String contents, FileInfo fileInfo, User author) {
        this.contents = contents;
        this.fileInfo = fileInfo;
        this.author = author;
    }

    public Article update(String contents, long editUserId) {
        checkAccessibleAuthor(editUserId);
        this.contents = contents;
        return this;
    }

    public void checkAccessibleAuthor(long editUserId) {
        if (notEqualAuthorId(editUserId)) {
            throw new InvalidUserException("글 작성자만 수정, 삭제가 가능합니다.");
        }
    }

    private boolean notEqualAuthorId(long id) {
        return this.author.notEqualId(id);
    }

    public Set<String> getKeyword() {
        Matcher matcher = Pattern.compile(REGEX).matcher(contents);

        Set<String> keywords = new HashSet<>();
        while (matcher.find()) {
            keywords.add(matcher.group());
        }
        return keywords;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", contents='" + contents + '\'' +
                ", fileInfo=" + fileInfo +
                ", author=" + author +
                '}';
    }
}
