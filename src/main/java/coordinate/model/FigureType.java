package coordinate.model;

import coordinate.Message;

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
        throw new IllegalArgumentException(Message.ERROR_INVALID_FIGURE_CREATION);
    }

    private boolean match(int size) {
        return this.size == size;
    }
}
