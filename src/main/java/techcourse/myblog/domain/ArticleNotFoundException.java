package techcourse.myblog.domain;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Can not find Article.")
public class ArticleNotFoundException extends RuntimeException {
    public ArticleNotFoundException() {
        super("해당하는 게시글을 찾지 못했습니다.");
    }
}
