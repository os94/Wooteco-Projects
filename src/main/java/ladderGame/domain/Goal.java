package ladderGame.domain;

import java.util.Objects;

public class Goal {
    private String goal;

    public Goal(String goal) {
        if (goal.trim().equals("")) {
            throw new IllegalArgumentException();
        }
        this.goal = goal;
    }

    public String getGoal() {
        return goal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Goal goal = (Goal) o;
        return Objects.equals(this.goal, goal.goal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(goal);
    }

    @Override
    public String toString() {
        return goal;
    }
}
