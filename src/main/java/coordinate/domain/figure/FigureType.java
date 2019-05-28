package coordinate.domain.figure;

public enum FigureType {
    LINE(2),
    TRIANGLE(3),
    RECTANGLE(4);

    private final int size;

    FigureType(int size) {
        this.size = size;
    }

    public static FigureType valueOf(int size) {
        for (FigureType figureType : FigureType.values()) {
            if (figureType.match(size)) {
                return figureType;
            }
        }
        throw new InvalidFigureException("유효하지 않은 도형입니다.");
    }

    private boolean match(int size) {
        return this.size == size;
    }
}
