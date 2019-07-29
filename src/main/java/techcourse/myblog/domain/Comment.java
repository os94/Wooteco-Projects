package techcourse.myblog.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "contents", nullable = false)
    private String contents;

    @Column(name = "time", nullable = false)
    private LocalDateTime time;

    @ManyToOne
    @JoinColumn(name = "author", foreignKey = @ForeignKey(name = "fk_comment_to_user"))
    private User author;

    @ManyToOne
    @JoinColumn(name = "article", foreignKey = @ForeignKey(name = "fk_comment_to_article"))
    private Article article;

    public Comment() {
    }

    public Comment(String contents, LocalDateTime time, User author, Article article) {
        this.contents = contents;
        this.time = time;
        this.author = author;
        this.article = article;
    }

    public long getId() {
        return id;
    }

    public String getContents() {
        return contents;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public User getAuthor() {
        return author;
    }

    public Article getArticle() {
        return article;
    }
}
