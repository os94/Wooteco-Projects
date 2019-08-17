package techcourse.myblog.dto;

import javax.validation.constraints.NotBlank;

public class CommentRequest {
    @NotBlank
    private String contents;

    public CommentRequest() {
    }

    public CommentRequest(@NotBlank String contents) {
        this.contents = contents;
    }

    public String getContents() {
        return contents;
    }

    @Override
    public String toString() {
        return "CommentRequest{" +
                "contents='" + contents + '\'' +
                '}';
    }
}
