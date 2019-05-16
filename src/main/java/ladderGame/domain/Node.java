package ladderGame.domain;

public class Node {
    private int row;
    private int column;

    public Node(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public void move(Direction direction) {
        if (direction == Direction.LEFT) {
            column--;
            row++;
        }
        if (direction == Direction.RIGHT) {
            column++;
            row++;
        }
        if (direction == Direction.DOWN) {
            row++;
        }
    }
}
