package coordinate.domain.figure;

public class InvalidFigureException extends  RuntimeException {
    public InvalidFigureException() {
        super();
    }

    public InvalidFigureException(String message) {
        super("InvalidFigureException : " + message);
    }
}
