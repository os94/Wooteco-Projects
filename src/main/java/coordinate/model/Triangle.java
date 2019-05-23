package coordinate.model;

import coordinate.Message;

import java.util.List;
import java.util.Objects;

public class Triangle extends AbstractFigure {
    private List<Point> points;

    public Triangle(List<Point> points) {
        if (isInStraightLine(points)) {
            throw new IllegalArgumentException(Message.ERROR_INVALID_TRIANGLE);
        }
        this.points = points;
    }

    private boolean isInStraightLine(List<Point> points) {
        return points.get(0).calculateSlope(points.get(1)) == points.get(0).calculateSlope(points.get(2));
    }

    @Override
    public boolean hasPoint(int x, int y) {
        return points.stream()
                .anyMatch(point -> point.hasPoint(x, y));
    }

    @Override
    public double area() {
        Point firstPoint = points.get(0);
        Point secondPoint = points.get(1);
        Point thirdPoint = points.get(2);

        double firstSide = firstPoint.calculateDistance(secondPoint);
        double secondSide = secondPoint.calculateDistance(thirdPoint);
        double thirdSide = thirdPoint.calculateDistance(firstPoint);

        return calculateFormulaOfHero(firstSide, secondSide, thirdSide);
    }

    private double calculateFormulaOfHero(double firstSide, double secondSide, double thirdSide) {
        double s = (firstSide + secondSide + thirdSide) / 2;

        return Math.sqrt(s * (s - firstSide) * (s - secondSide) * (s - thirdSide));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Triangle triangle = (Triangle) o;
        return Objects.equals(points, triangle.points);
    }

    @Override
    public int hashCode() {
        return Objects.hash(points);
    }
}
