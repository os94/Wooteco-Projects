package http.exception;

public class InvalidHttpHeaderException extends RuntimeException {
    public InvalidHttpHeaderException(String message) {
        super(message);
    }
}
