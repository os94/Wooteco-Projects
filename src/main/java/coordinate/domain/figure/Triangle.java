package coordinate.domain.figure;

import coordinate.domain.Point;

import java.util.List;

class Triangle extends AbstractFigure {
    Triangle(List<Point> points) {
        super(points);
    }

    @Override
    public int size() {
        return 3;
    }

    @Override
    public String getName() {
        return "삼각형";
    }

    @Override
    public double area() {
        return 0;
    }
}
