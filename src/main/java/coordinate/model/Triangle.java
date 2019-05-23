package coordinate.model;

import coordinate.Message;

import java.util.List;

public class Triangle extends AbstractFigure {
    public Triangle(List<Point> points) {
        super(points);
        if (isInStraightLine(points)) {
            throw new IllegalArgumentException(Message.ERROR_INVALID_TRIANGLE);
        }
    }

    private boolean isInStraightLine(List<Point> points) {
        return points.get(0).calculateSlope(points.get(1)) == points.get(0).calculateSlope(points.get(2));
    }

    @Override
    public double area() {
        Point firstPoint = getPoints().get(0);
        Point secondPoint = getPoints().get(1);
        Point thirdPoint = getPoints().get(2);

        double firstSide = firstPoint.calculateDistance(secondPoint);
        double secondSide = secondPoint.calculateDistance(thirdPoint);
        double thirdSide = thirdPoint.calculateDistance(firstPoint);

        return calculateFormulaOfHero(firstSide, secondSide, thirdSide);
    }

    private double calculateFormulaOfHero(double firstSide, double secondSide, double thirdSide) {
        double s = (firstSide + secondSide + thirdSide) / 2;

        return Math.sqrt(s * (s - firstSide) * (s - secondSide) * (s - thirdSide));
    }
}
