package ladderGame.domain;

import ladderGame.constant.Contants;

import java.util.*;

public class GameData {
    private List<Member> members;
    private List<Goal> goals;

    public GameData(String members, String goals) {
        this.members = makeMembers(members.split(Contants.DELIMITER_COMMA));
        this.goals = makeGoal(goals.split(Contants.DELIMITER_COMMA));

        if (this.members.size() != this.goals.size()) {
            throw new IllegalArgumentException(Contants.ERROR_MISMATCH_MEMBER_AND_GOAL);
        }
    }

    public List<Member> getMembers() {
        return members;
    }

    public List<Goal> getGoals() {
        return goals;
    }

    public String getName(int index) {
        return members.get(index).toString();
    }

    public String getGoal(int index) {
        return goals.get(index).toString();
    }

    public int getCountOfPerson() {
        return members.size();
    }

    public int getMemberIndex(String name) {
        return members.indexOf(new Member(name));
    }

    public boolean hasMember(String name) {
        return members.contains(new Member(name));
    }

    private List<Member> makeMembers(String[] names) {
        List<Member> members = new ArrayList<>();

        if (hasDuplicateName(names)) {
            throw new IllegalArgumentException(Contants.ERROR_DUPLICATE_NAME);
        }
        for (String name : names) {
            members.add(new Member(name));
        }

        return members;
    }

    private List<Goal> makeGoal(String[] inputGoals) {
        List<Goal> goals = new ArrayList<>();

        for (String goal : inputGoals) {
            goals.add(new Goal(goal));
        }

        return goals;
    }

    private boolean hasDuplicateName(String[] names) {
        return names.length != new HashSet<>(Arrays.asList(names)).size();
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
