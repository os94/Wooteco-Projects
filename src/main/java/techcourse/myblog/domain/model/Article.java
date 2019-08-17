package techcourse.myblog.domain.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "article")
public class Article extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "coverUrl", nullable = false)
    private String coverUrl;

    @Column(name = "contents", nullable = false)
    @Lob
    @Basic(fetch = FetchType.LAZY)
    private String contents;

    @ManyToOne
    @JoinColumn(name = "author", foreignKey = @ForeignKey(name = "fk_article_to_user"), nullable = false)
    private User author;

    public Article() {
    }

    public Article(String title, String coverUrl, String contents) {
        this.title = title;
        this.coverUrl = coverUrl;
        this.contents = contents;
    }

    public Article update(Article articleToUpdate) {
        this.title = articleToUpdate.title;
        this.coverUrl = articleToUpdate.coverUrl;
        this.contents = articleToUpdate.contents;
        return this;
    }

    public boolean isAuthor(User user) {
        return this.author.equals(user);
    }

    public void setAuthor(User persistUser) {
        this.author = persistUser;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public String getContents() {
        return contents;
    }

    public User getAuthor() {
        return author;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        return id == article.id &&
                Objects.equals(title, article.title) &&
                Objects.equals(coverUrl, article.coverUrl) &&
                Objects.equals(contents, article.contents) &&
                Objects.equals(author, article.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, coverUrl, contents, author);
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", coverUrl='" + coverUrl + '\'' +
                ", contents='" + contents + '\'' +
                ", author=" + author +
                '}';
    }
}