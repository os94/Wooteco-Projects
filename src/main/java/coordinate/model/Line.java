package coordinate.model;

import java.util.List;

public class Line extends AbstractFigure {
    private static final int SIZE = 2;
    private static final String NAME = "선";

    public Line(List<Point> points) {
        super(points);
    }

    @Override
    public int size() {
        return SIZE;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public double area() {
        return getPoint(0).calculateDistance(getPoint(1));
    }
}
