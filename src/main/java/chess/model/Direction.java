package chess.model;

public enum Direction {
    NORTH(0, 1),
    NORTHWEST(-1, 1),
    NORTHEAST(1, 1);

    private int xVector;
    private int yVector;

    Direction(int xVector, int yVector) {
        this.xVector = xVector;
        this.yVector = yVector;
    }
}
