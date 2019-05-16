package ladderGame.domain;

import java.util.Objects;

public class Goal {
    private String playResult;

    public Goal(String playResult) {
        if (playResult.trim().equals("")) {
            throw new IllegalArgumentException();
        }

        this.playResult = playResult;
    }

    public String getPlayResult() {
        return playResult;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Goal goal = (Goal) o;
        return Objects.equals(playResult, goal.playResult);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playResult);
    }

    @Override
    public String toString() {
        return playResult;
    }
}
