package ladderGame.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Ladder {
    private static final int FIRST_COLUMN = 0;

    private List<Line> lines;

    public Ladder(int countOfPerson, int height) {
        lines = new ArrayList<>();

        for (int i = 0; i < height; i++) {
            lines.add(new Line(countOfPerson));
        }
    }

    public List<Line> getLines() {
        return lines;
    }

    public void connectBridges() {
        for (Line line : lines) {
            line.connectBridges();
        }
    }

    public boolean isConnected(int row, int column) {
        return lines.get(row).isConnected(column);
    }

    public boolean atDestination(Node currentNode) {
        return currentNode.getRow() >= lines.size();
    }

    public boolean atFirstColumn(Node currentNode) {
        return currentNode.getColumn() == FIRST_COLUMN;
    }

    public boolean atLastColumn(Node currentNode) {
        return currentNode.getColumn() == lines.get(currentNode.getRow()).getSize();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ladder ladder = (Ladder) o;
        return Objects.equals(lines, ladder.lines);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lines);
    }
}
