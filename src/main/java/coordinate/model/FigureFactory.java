package coordinate.model;

import coordinate.Message;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static coordinate.model.FigureType.*;

public class FigureFactory {
    private static Map<FigureType, Function<List<Point>, Figure>> figureClassifier = new HashMap<>();

    static {
        figureClassifier.put(LINE, Line::new);
        figureClassifier.put(TRIANGLE, Triangle::new);
        figureClassifier.put(RECTANGLE, Rectangle::new);
    }

    public static Figure create(List<Point> points) {
        if (points == null) {
            throw new IllegalArgumentException(Message.ERROR_FIGURE_NULL);
        }
        checkDuplicationOf(points);
        return figureClassifier
                .get(FigureType.valueOf(points.size()))
                .apply(points);
    }

    private static void checkDuplicationOf(List<Point> points) {
        if (points.size() != new HashSet<>(points).size()) {
            throw new IllegalArgumentException(Message.ERROR_DUPLICATE_POINTS);
        }
    }
}
