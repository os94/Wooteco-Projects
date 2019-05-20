package ladderGame.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Ladder {
    private static final int FIRST_COLUMN = 0;

    private List<Row> rows;

    public Ladder(int rowNumber, int columnNumber) {
        rows = new ArrayList<>();

        for (int i = 0; i < rowNumber; i++) {
            rows.add(new Row(columnNumber));
        }
    }

    public List<Row> getRows() {
        return rows;
    }

    public void connectBridges() {
        for (Row row : rows) {
            row.connectBridges();
        }
    }

    public boolean isConnected(int row, int column) {
        return rows.get(row).isConnected(column);
    }

    public boolean atDestination(Node currentNode) {
        return currentNode.getRow() >= rows.size();
    }

    public boolean atFirstColumn(Node currentNode) {
        return currentNode.getColumn() == FIRST_COLUMN;
    }

    public boolean atLastColumn(Node currentNode) {
        return currentNode.getColumn() == rows.get(currentNode.getRow()).getSize();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ladder ladder = (Ladder) o;
        return Objects.equals(rows, ladder.rows);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rows);
    }
}
