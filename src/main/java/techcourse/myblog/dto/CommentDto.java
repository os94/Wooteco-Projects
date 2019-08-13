package techcourse.myblog.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CommentDto {
    @NotBlank
    private String contents;

    @NotNull
    private long articleId;

    public CommentDto() {
    }

    public CommentDto(@NotBlank String contents, @NotNull long articleId) {
        this.contents = contents;
        this.articleId = articleId;
    }

    public String getContents() {
        return contents;
    }

    public long getArticleId() {
        return articleId;
    }

    @Override
    public String toString() {
        return "CommentDto{" +
                "contents='" + contents + '\'' +
                ", articleId=" + articleId +
                '}';
    }
}
