package coordinate.domain.figure;

import coordinate.domain.Point;

import java.util.List;

public enum FigureType {
    LINE(2) {
        @Override
        public Figure create(List<Point> points) {
            return new Line(points);
        }
    },
    TRIANGLE(3) {
        @Override
        public Figure create(List<Point> points) {
            return new Triangle(points);
        }
    },
    RECTANGLE(4) {
        @Override
        public Figure create(List<Point> points) {
            return new Rectangle(points);
        }
    };

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
        throw new IllegalArgumentException("유효하지 않은 도형입니다.");
    }

    private boolean match(int size) {
        return this.size == size;
    }

    public Figure create(List<Point> points) {
        return null;
    }
}
