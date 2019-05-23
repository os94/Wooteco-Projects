package coordinate.model;

import coordinate.Message;

import java.util.List;

public abstract class AbstractFigure {
    private static final int ONE_POINT = 1;
    private static final int NUM_OF_VERTICES_OF_LINE = 2;
    private static final int NUM_OF_VERTICES_OF_TRIANGLE = 3;
    private static final int NUM_OF_VERTICES_OF_RECTANGLE = 4;

    AbstractFigure() {
    }

    public static AbstractFigure create(List<Point> points) {
        if (points == null) {
            throw new IllegalArgumentException(Message.ERROR_FIGURE_NULL);
        }
        return classifyFigure(points);
    }

    private static AbstractFigure classifyFigure(List<Point> points) {
        if (points.size() == ONE_POINT) {
            return points.get(0);
        }
        if (points.size() == NUM_OF_VERTICES_OF_LINE) {
            return new Line(points);
        }
        if (points.size() == NUM_OF_VERTICES_OF_TRIANGLE) {
            return new Triangle(points);
        }
        if (points.size() == NUM_OF_VERTICES_OF_RECTANGLE) {
            return new Rectangle(points);
        }
        throw new IllegalArgumentException(Message.ERROR_INVALID_FIGURE_CREATION);
    }

    public abstract boolean hasPoint(int x, int y);

    public abstract double area();
}
