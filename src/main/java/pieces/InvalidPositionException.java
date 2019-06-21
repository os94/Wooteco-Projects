package pieces;

public class InvalidPositionException extends RuntimeException {
    private static final long serialVersionUID = 100833885452365856L;

    public InvalidPositionException(String message) {
        super(message);
    }
}