package chess.model;

public class Point {
    private int x;
    private int y;

    private Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point of(int x, int y) {
        return new Point(x, y);
    }

    public double calculateDistance(Point other) {
        return Math.sqrt(Math.pow(other.x - this.x, 2) + Math.pow(other.y - this.y, 2));
    }

    public int xDistanceFrom(Point other) {
        return other.x - this.x;
    }

    public int yDistanceFrom(Point other) {
        return other.y - this.y;
    }
}
