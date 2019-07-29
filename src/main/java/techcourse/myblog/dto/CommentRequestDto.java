package techcourse.myblog.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CommentRequestDto {
    @NotBlank
    private String contents;

    @NotNull
    private long articleId;

    public CommentRequestDto(@NotBlank String contents, @NotNull long articleId) {
        this.contents = contents;
        this.articleId = articleId;
    }

    public String getContents() {
        return contents;
    }

    public long getArticleId() {
        return articleId;
    }
}
