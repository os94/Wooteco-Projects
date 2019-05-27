package coordinate.domain.figure;

import coordinate.domain.Point;

import java.util.List;

class Rectangle extends AbstractFigure {
    Rectangle(List<Point> points) {
        super(points);
    }

    @Override
    public int size() {
        return 4;
    }

    @Override
    public String getName() {
        return "사각형";
    }

    @Override
    public double area() {
        return 0;
    }
}
