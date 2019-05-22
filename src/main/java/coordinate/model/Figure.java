package coordinate.model;

import coordinate.Message;

import java.util.List;

public abstract class Figure {
    private static final int ONE_POINT = 1;
    private static final int NUM_OF_VERTICES_OF_LINE = 2;

    Figure() {
    }

    public static Figure create(List<Point> points) {
        if (points == null) {
            throw new IllegalArgumentException(Message.ERROR_FIGURE_NULL);
        }
        return classifyFigure(points);
    }

    private static Figure classifyFigure(List<Point> points) {
        if (points.size() == ONE_POINT) {
            return points.get(0);
        }
        if (points.size() == NUM_OF_VERTICES_OF_LINE) {
            return new Line(points);
        }
        throw new IllegalArgumentException(Message.ERROR_INVALID_FIGURE_CREATION);
    }

    public abstract boolean hasPoint(int x, int y);

    public abstract double area();
}
