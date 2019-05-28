package coordinate.domain.figure;

public class InvalidPositionException extends RuntimeException {
    public InvalidPositionException() {
        super();
    }

    public InvalidPositionException(String message) {
        super("InvalidPositionException : " + message);
    }
}
