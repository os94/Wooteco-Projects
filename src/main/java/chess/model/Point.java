package chess.model;

import java.util.Objects;

public class Point {
    private int x;
    private int y;

    private Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Point of(int x, int y) {
        return new Point(x, y);
    }

    public double calculateDistance(Point other) {
        return Math.sqrt(Math.pow(other.x - this.x, 2) + Math.pow(other.y - this.y, 2));
    }

    public Direction calculateDirection(Point other) {
        int xDistance = other.x - this.x;
        int yDistance = other.y - this.y;
        return Direction.valueOf(xDistance, yDistance);
    }

    public Point next(Direction direction) {
        return of(x + direction.getXDegree(), y + direction.getYDegree());
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
}
