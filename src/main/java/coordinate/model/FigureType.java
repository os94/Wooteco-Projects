package coordinate.model;

import java.util.Arrays;

public enum FigureType {
    LINE(2),
    TRIANGLE(3),
    RECTANGLE(4);

    private final int size;

    FigureType(int size) {
        this.size = size;
    }

    public static FigureType valueOf(int size) {
        return Arrays.stream(FigureType.values())
                .filter(type -> type.match(size))
                .findFirst()
                .orElseThrow(InvalidFigureTypeException::new)
                ;
    }

    private boolean match(int size) {
        return this.size == size;
    }
}
