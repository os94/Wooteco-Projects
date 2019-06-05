package coordinate.model;

public class InvalidFigureTypeException extends RuntimeException {
    private static final String ERROR_INVALID_FIGURE_TYPE = "입력된 Point 개수가 유효하지 않습니다.";

    public InvalidFigureTypeException() {
        super(ERROR_INVALID_FIGURE_TYPE);
    }
}
