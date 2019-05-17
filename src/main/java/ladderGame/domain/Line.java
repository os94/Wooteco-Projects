package ladderGame.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Line {
    private List<Boolean> bridges;

    public Line(int countOfPerson) {
        bridges = new ArrayList<>();

        for (int i = 0; i < countOfPerson - 1; i++) {
            bridges.add(false);
        }
    }

    public void connectBridges() {
        for (int i = 0; i < bridges.size(); i++) {
            connectBridge(i);
        }
    }

    public boolean isConnected(int column) {
        return bridges.get(column);
    }

    public int getSize() {
        return bridges.size();
    }

    private void connectBridge(int index) {
        if (RandomGenerator.generate() && isConnectable(index)) {
            bridges.set(index, true);
        }
    }

    private boolean isConnectable(int index) {
        if (index == 0) {
            return true;
        }
        return !bridges.get(index - 1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Line line = (Line) o;
        return Objects.equals(bridges, line.bridges);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bridges);
    }
}
