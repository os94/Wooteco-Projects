package ladderGame.domain;

import java.util.List;
import java.util.Objects;

public class GameData {
    private List<Member> members;
    private List<Goal> goals;

    public GameData(List<Member> members, List<Goal> goals) {
        if (members.size() != goals.size()) {
            throw new IllegalArgumentException();
        }

        this.members = members;
        this.goals = goals;
    }

    public List<Member> getMembers() {
        return members;
    }

    public List<Goal> getGoals() {
        return goals;
    }

    public int getCountOfPerson() {
        return members.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameData gameData = (GameData) o;
        return Objects.equals(members, gameData.members) &&
                Objects.equals(goals, gameData.goals);
    }

    @Override
    public int hashCode() {
        return Objects.hash(members, goals);
    }
}
