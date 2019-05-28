package coordinate.domain.figure;

import coordinate.domain.Point;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static coordinate.domain.figure.FigureType.*;

public class FigureFactory {
    private static Map<FigureType, FigureCreator> creators = new HashMap<>();

    static {
        creators.put(LINE, Line::new);
        creators.put(TRIANGLE, Triangle::new);
        creators.put(RECTANGLE, Rectangle::new);
    }

    public static Figure getFigure(List<Point> points) {
        FigureCreator figureCreator = creators.get(FigureType.valueOf(points.size()));
        return figureCreator.create(points);
    }
}
