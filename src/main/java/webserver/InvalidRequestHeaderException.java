package webserver;

public class InvalidRequestHeaderException extends RuntimeException {
    public InvalidRequestHeaderException(String message) {
        super(message);
    }
}
