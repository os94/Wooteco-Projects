package coordinate.model;

import coordinate.Message;

import java.util.*;

public abstract class AbstractFigure implements Figure {
    private final List<Point> points;

    AbstractFigure(List<Point> points) {
        if (points == null || points.isEmpty()) {
            throw new IllegalArgumentException(Message.ERROR_FIGURE_NULL);
        }
        if (points.size() != size()) {
            throw new IllegalArgumentException(getName() + Message.ERROR_MISMATCH_POINT_SIZE_1
                    + size() + Message.ERROR_MISMATCH_POINT_SIZE_2);
        }
        this.points = new ArrayList<>(points);
    }

    public Point getPoint(int index) {
        return points.get(index);
    }

    @Override
    public Map<Integer, Integer> getXYCoordinates() {
        Map<Integer, Integer> xAndY = new HashMap<>();
        for (Point point : points) {
            xAndY.put(point.getX(), point.getY());
        }
        return xAndY;
    }

    @Override
    public List<Point> getPoints() {
        return Collections.unmodifiableList(points);
    }

    @Override
    public boolean hasPoint(Point other) {
        return points
                .stream()
                .anyMatch(point -> point.equals(other))
                ;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final AbstractFigure that = (AbstractFigure) o;
        return Objects.equals(points, that.points);
    }

    @Override
    public int hashCode() {
        return Objects.hash(points);
    }
}
