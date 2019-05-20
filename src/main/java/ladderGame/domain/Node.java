package ladderGame.domain;

import java.util.Objects;

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

    public Node previous() {
        return new Node(row, column - 1);
    }

    public boolean isConnected(Ladder ladder) {
        return ladder.isConnected(row, column);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return row == node.row &&
                column == node.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }
}
