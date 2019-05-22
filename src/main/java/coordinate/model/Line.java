package coordinate.model;

import java.util.List;
import java.util.Objects;

public class Line extends Figure {
    private List<Point> points;

    public Line(List<Point> points) {
        this.points = points;
    }

    @Override
    public boolean hasPoint(int x, int y) {
        return points.stream().anyMatch(point -> point.hasPoint(x, y));
    }

    @Override
    public double area() {
        Point firstPoint = points.get(0);
        Point secondPoint = points.get(1);
        return Math.sqrt(squareDifference(firstPoint.getX(), secondPoint.getX())
                + squareDifference(firstPoint.getY(), secondPoint.getY()));
    }

    private double squareDifference(int firstValue, int secondValue) {
        return Math.pow(firstValue - secondValue, 2);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Line line = (Line) o;
        return Objects.equals(points, line.points);
    }

    @Override
    public int hashCode() {
        return Objects.hash(points);
    }
}
