package ladderGame.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Ladder {
    private List<Line> lines;

    public Ladder(int countOfPerson, int height) {
        lines = new ArrayList<>();

        for (int i = 0; i < height; i++) {
            lines.add(new Line(countOfPerson));
        }
    }

    public void connectLine() {
        for (Line line : lines) {
            line.connectLines();
        }
    }

    public List<Line> getLines() {
        return lines;
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
