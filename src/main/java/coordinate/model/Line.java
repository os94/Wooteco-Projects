package coordinate.model;

import coordinate.Message;

import java.util.List;

public class Line extends AbstractFigure {
    private static final int SIZE = 2;
    private static final String NAME = "선";

    Line(List<Point> points) {
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

    @Override
    public String getAreaInfo() {
        return Message.OUTPUT_DISTANCE_OF_LINE + area();
    }
}
