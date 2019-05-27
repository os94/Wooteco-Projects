package coordinate.domain.figure;

import coordinate.domain.Point;

import java.util.List;

class Line extends AbstractFigure {
    Line(List<Point> points) {
        super(points);
    }

    @Override
    public int size() {
        return 2;
    }

    @Override
    public String getName() {
        return "선";
    }

    @Override
    public double area() {
        return 0;
    }
}
