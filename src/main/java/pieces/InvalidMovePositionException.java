package pieces;

public class InvalidMovePositionException extends RuntimeException {
    private static final long serialVersionUID = -2262018355736231053L;

    public InvalidMovePositionException(String message) {
        super(message);
    }
}
