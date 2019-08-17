package techcourse.myblog.dto;

import java.time.LocalDateTime;

public class CommentResponse {
    private long id;
    private String contents;
    private LocalDateTime lastModifiedDate;
    private String commenter;

    public CommentResponse(long id, String contents, LocalDateTime lastModifiedDate, String commenter) {
        this.id = id;
        this.contents = contents;
        this.lastModifiedDate = lastModifiedDate;
        this.commenter = commenter;
    }

    public long getId() {
        return id;
    }

    public String getContents() {
        return contents;
    }

    public LocalDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public String getCommenter() {
        return commenter;
    }
}
