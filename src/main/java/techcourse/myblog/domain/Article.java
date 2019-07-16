package techcourse.myblog.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Article {

    private static final String DEFAULT_COVER_URL = "/images/pages/index/study.jpg";
    private static final int NO_COVER_URL = 0;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;
    private String coverUrl;
    private String contents;

    public Article() {
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

    public void setId(long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = (coverUrl.length() != NO_COVER_URL) ? coverUrl : DEFAULT_COVER_URL;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }
}