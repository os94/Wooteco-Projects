package coordinate.model;

import coordinate.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

public class Rectangle extends AbstractFigure {
    private static final int SIZE = 4;
    private static final String NAME = "사각형";
    private static final int NUM_OF_VERTEX_IN_RECTANGLE = 2;

    Rectangle(List<Point> points) {
        super(points);
        checkIsRectangle();
    }

    private void checkIsRectangle() {
        Set<Integer> xValuesOfPoints = getPoints().stream()
                .map(Point::getX)
                .collect(toSet());
        Set<Integer> yValuesOfPoints = getPoints().stream()
                .map(Point::getY)
                .collect(toSet());

        if (hasNotTwoPoints(xValuesOfPoints) || hasNotTwoPoints(yValuesOfPoints)) {
            throw new IllegalArgumentException(NAME + Message.ERROR_INVALID_SHAPE);
        }
    }

    private boolean hasNotTwoPoints(Set<Integer> valuesOfPoints) {
        return valuesOfPoints.size() != NUM_OF_VERTEX_IN_RECTANGLE;
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
