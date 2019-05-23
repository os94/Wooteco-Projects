package coordinate.model;

import coordinate.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

public class Rectangle extends AbstractFigure {
    public Rectangle(List<Point> points) {
        super(points);
        checkRectangleWith(points);
    }

    private void checkRectangleWith(List<Point> points) {
        Set<Integer> xValuesOfPoints = points.stream()
                .map(Point::getX)
                .collect(toSet());
        Set<Integer> yValuesOfPoints = points.stream()
                .map(Point::getY)
                .collect(toSet());
        if (hasNotTwoPoints(xValuesOfPoints) || hasNotTwoPoints(yValuesOfPoints)) {
            throw new IllegalArgumentException(Message.ERROR_INVALID_RECTANGLE);
        }
    }

    private boolean hasNotTwoPoints(Set<Integer> valuesOfPoints) {
        return valuesOfPoints.size() != 2;
    }

    @Override
    public double area() {
        int differenceOfXValues = calculateDifference(getPoints().stream()
                .map(Point::getX)
                .collect(toSet()));
        int differenceOfYValues = calculateDifference(getPoints().stream()
                .map(Point::getY)
                .collect(toSet()));

        return (double) (differenceOfXValues * differenceOfYValues);
    }

    private int calculateDifference(Set<Integer> valuesOfPoints) {
        List<Integer> values = new ArrayList<>(valuesOfPoints);
        return Math.abs(values.get(0) - values.get(1));
    }
}
