package ladderGame.domain;

import java.util.*;

public class Ladder {
    private static final int FIRST_ROW = 0;
    private static final int FIRST_COLUMN = 0;

    private List<Row> rows;

    public Ladder(int rowNumber, int columnNumber) {
        rows = new ArrayList<>();

        for (int i = 0; i < rowNumber; i++) {
            rows.add(new Row(columnNumber));
        }
    }

    public void connect() {
        for (int rowIndex = 0; rowIndex < rows.size(); rowIndex++) {
            for (int columnIndex = 0; columnIndex < getNumberOfColumn() - 1; columnIndex++) {
                connectBridge(rowIndex, columnIndex, RandomGenerator.generate());
            }
        }
    }

    public void connectBridge(int rowIndex, int columnIndex, boolean connectOrNot) {
        rows.get(rowIndex).connectBridge(columnIndex, connectOrNot);
    }

    public LadderResult play() {
        Map<Integer, Integer> result = new HashMap<>();

        for (int startIndex = 0; startIndex < getNumberOfColumn(); startIndex++) {
            int destinationIndex = getDestination(startIndex);
            result.put(startIndex, destinationIndex);
        }

        return new LadderResult(result);
    }

    private int getDestination(int startIndex) {
        Node currentNode = new Node(FIRST_ROW, startIndex);

        do {
            LadderDirection direction = LadderDirection.valueOf(this, currentNode);
            currentNode = direction.move(currentNode);
        } while (!atDestination(currentNode));

        return currentNode.getColumn();
    }

    public List<Row> getRows() {
        return rows;
    }

    public int getNumberOfColumn() {
        return rows.get(FIRST_ROW).size();
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
        return currentNode.getColumn() + 1 == getNumberOfColumn();
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
