package techcourse.myblog.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "Mismatch User.")
public class MisMatchUserException extends RuntimeException {
    public MisMatchUserException(String message) {
        super(message);
    }
}
