package techcourse.myblog.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Mismatch Password.")
public class MisMatchPasswordException extends RuntimeException {
    public MisMatchPasswordException(String message) {
        super(message);
    }
}
