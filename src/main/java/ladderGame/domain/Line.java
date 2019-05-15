package ladderGame.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Line {
    private List<Boolean> points;

    public Line(int countOfPerson) {
        points = new ArrayList<>();

        for (int i = 0; i < countOfPerson - 1; i++) {
            points.add(false);
        }
    }

    public void connectLines() {
        for (int i = 0; i < points.size(); i++) {
            connectBridge(i);
        }
    }

    public boolean isConnected(int i) {
        return points.get(i);
    }

    private void connectBridge(int index) {
        int number = new Random().nextInt(2);

        if (number == 0 && isConnectable(index)) {
            points.set(index, true);
        }
    }

    private boolean isConnectable(int index) {
        if (index == 0) {
            return true;
        }

        return !points.get(index - 1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Line line = (Line) o;
        return Objects.equals(points, line.points);
    }

    @Override
    public int hashCode() {
        return Objects.hash(points);
    }
}
