package techcourse.myblog.dto;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.unmodifiableList;

public class CommentsResponse {
    private List<CommentResponse> comments;

    public CommentsResponse(List<CommentResponse> comments) {
        this.comments = new ArrayList<>(comments);
    }

    public List<CommentResponse> getComments() {
        return unmodifiableList(comments);
    }
}
