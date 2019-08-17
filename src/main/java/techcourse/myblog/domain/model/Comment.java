package techcourse.myblog.domain.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Comment extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "contents", nullable = false)
    private String contents;

    @ManyToOne
    @JoinColumn(name = "author", foreignKey = @ForeignKey(name = "fk_comment_to_user"), nullable = false)
    private User author;

    @ManyToOne
    @JoinColumn(name = "article", foreignKey = @ForeignKey(name = "fk_comment_to_article"), nullable = false)
    private Article article;

    public Comment() {
    }

    public Comment(String contents, User author, Article article) {
        this.contents = contents;
        this.author = author;
        this.article = article;
    }

    public boolean isAuthor(User user) {
        return this.author.equals(user);
    }

    public Comment update(String contents) {
        this.contents = contents;
        return this;
    }

    public long getId() {
        return id;
    }

    public String getContents() {
        return contents;
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
                Objects.equals(author, comment.author) &&
                Objects.equals(article, comment.article);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, contents, author, article);
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", contents='" + contents + '\'' +
                ", author=" + author +
                ", article=" + article +
                '}';
    }
}
