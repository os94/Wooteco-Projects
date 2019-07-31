package techcourse.myblog.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "Mismatch Author.")
public class MisMatchAuthorException extends RuntimeException {
    public MisMatchAuthorException(String message) {
        super(message);
    }
}
