package coordinate.model;

import coordinate.Message;

import java.util.List;

public class Triangle extends AbstractFigure {
    private static final int SIZE = 3;
    private static final String NAME = "삼각형";

    Triangle(List<Point> points) {
        super(points);
        if (isInStraightLine()) {
            throw new IllegalArgumentException(NAME + Message.ERROR_INVALID_SHAPE);
        }
    }

    private boolean isInStraightLine() {
        return getPoint(0).calculateSlope(getPoint(1))
                == getPoint(0).calculateSlope(getPoint(2));
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
        Point firstPoint = getPoint(0);
        Point secondPoint = getPoint(1);
        Point thirdPoint = getPoint(2);

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
    public String getAreaInfo() {
        return NAME + Message.OUTPUT_AREA + area();
    }
}
