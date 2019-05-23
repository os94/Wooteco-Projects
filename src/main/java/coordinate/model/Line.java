package coordinate.model;

import java.util.List;

public class Line extends AbstractFigure {
    public Line(List<Point> points) {
        super(points);
    }

    @Override
    public double area() {
        return getPoints().get(0).calculateDistance(getPoints().get(1));
    }
}
