package techcourse.myblog.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

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
    @JoinColumn(name = "author", foreignKey = @ForeignKey(name = "fk_comment_to_user"), nullable = false)
    private User author;

    @ManyToOne
    @JoinColumn(name = "article", foreignKey = @ForeignKey(name = "fk_comment_to_article"), nullable = false)
    private Article article;

    public Comment() {
    }

    public Comment(String contents, LocalDateTime time, User author, Article article) {
        this.contents = contents;
        this.time = time;
        this.author = author;
        this.article = article;
    }

    public void setContents(String contents) {
        this.contents = contents;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return id == comment.id &&
                Objects.equals(contents, comment.contents) &&
                Objects.equals(time, comment.time) &&
                Objects.equals(author, comment.author) &&
                Objects.equals(article, comment.article);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, contents, time, author, article);
    }
}
