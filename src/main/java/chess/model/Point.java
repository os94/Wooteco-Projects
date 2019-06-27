package chess.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Point {
    private static final String DELIMITER_COLON = ",";
    private static final int MAX_RANGE_OF_POINT = 8;
    private static final int MIN_RANGE_OF_POINT = 1;
    private static final Map<String, Point> points = new HashMap<>();
    private final int x;
    private final int y;

    static {
        for (int x = 1; x <= MAX_RANGE_OF_POINT; x++) {
            setPoints(x);
        }
    }

    private Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Point of(int x, int y) {
        checkValidPoint(x, y);
        return points.get(x + DELIMITER_COLON + y);
    }

    public Point next(Direction direction) {
        return of(x + direction.getXDegree(), y + direction.getYDegree());
    }

    public double calculateDistance(Point other) {
        return Math.sqrt(Math.pow(other.x - this.x, 2) + Math.pow(other.y - this.y, 2));
    }

    public Direction calculateDirection(Point other) {
        int xDistance = other.x - this.x;
        int yDistance = other.y - this.y;
        return Direction.valueOf(xDistance, yDistance);
    }

    private static void setPoints(int x) {
        for (int y = 1; y <= MAX_RANGE_OF_POINT; y++) {
            points.put(x + DELIMITER_COLON + y, new Point(x, y));
        }
    }

    private static void checkValidPoint(int x, int y) {
        if (MIN_RANGE_OF_POINT > x || MAX_RANGE_OF_POINT < x || MIN_RANGE_OF_POINT > y || MAX_RANGE_OF_POINT < y) {
            throw new IllegalArgumentException("올바르지 않은 위치입니다.");
        }
    }

    public int getX() {
        return x;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return x == point.x &&
                y == point.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return x + DELIMITER_COLON + y;
    }
}
