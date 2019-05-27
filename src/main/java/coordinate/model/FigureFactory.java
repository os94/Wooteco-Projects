package coordinate.model;

import coordinate.Message;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class FigureFactory {
    private static final int ONE_POINT = 1;
    private static final int LINE_VERTEX = 2;
    private static final int TRIANGLE_VERTEX = 3;
    private static final int RECTANGLE_VERTEX = 4;

    private static Map<Integer, Function<List<Point>, Figure>> figureClassifier = new HashMap<>();

    static {
        figureClassifier.put(LINE_VERTEX, Line::new);
        figureClassifier.put(TRIANGLE_VERTEX, Triangle::new);
        figureClassifier.put(RECTANGLE_VERTEX, Rectangle::new);
    }

    public static Figure create(List<Point> points) {
        checkNumberOf(points);
        return figureClassifier.get(points.size()).apply(points);
    }

    private static void checkNumberOf(List<Point> points) {
        if (points == null) {
            throw new IllegalArgumentException(Message.ERROR_FIGURE_NULL);
        }
        if (points.size() == ONE_POINT) {
            throw new IllegalArgumentException(Message.ERROR_INVALID_FIGURE_CREATION);
        }
        if (points.size() > RECTANGLE_VERTEX) {
            throw new IllegalArgumentException(Message.ERROR_INVALID_FIGURE_CREATION);
        }
    }
}
