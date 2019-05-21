package coordinate.model;

import coordinate.Message;

import java.util.List;

public class Figure {
    public static Figure create(List<Point> points) {
        if (points == null) {
            throw new IllegalArgumentException(Message.ERROR_FIGURE_NULL);
        }
        if (points.size() == 1) {
            return points.get(0);
        }
        if (points.size() == 2) {
            return new Line(points);
        }
        throw new IllegalArgumentException(Message.ERROR_INVALID_FIGURE_CREATION);
    }
}
