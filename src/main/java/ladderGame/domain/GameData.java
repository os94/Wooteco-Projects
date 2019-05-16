package ladderGame.domain;

import ladderGame.constant.MessageContants;

import java.util.*;

public class GameData {
    private List<Member> members;
    private List<Goal> goals;

    public GameData(String members, String goals) {
        this.members = makeMembers(members.split(MessageContants.DELIMITER_COMMA));
        this.goals = makeGoal(goals.split(MessageContants.DELIMITER_COMMA));

        if (this.members.size() != this.goals.size()) {
            throw new IllegalArgumentException();
        }
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

    public boolean hasMember(String target) {
        return members.contains(new Member(target));
    }

    public int getIndex(String target) {
        return members.indexOf(new Member(target));
    }

    public String getResult(int index) {
        return goals.get(index).toString();
    }

    public String getName(int index) {
        return members.get(index).toString();
    }


    private List<Member> makeMembers(String[] names) {
        List<Member> memberGroup = new ArrayList<>();

        if (hasDuplicateName(names)) {
            throw new IllegalArgumentException(MessageContants.ERROR_DUPLICATE_NAME);
        }
        for (String name : names) {
            memberGroup.add(new Member(name));
        }

        return memberGroup;
    }

    private List<Goal> makeGoal(String[] results) {
        List<Goal> goals = new ArrayList<>();

        for (String result : results) {
            goals.add(new Goal(result));
        }

        return goals;
    }

    private boolean hasDuplicateName(String[] names) {
        return new HashSet<>(Arrays.asList(names)).size() != names.length;
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
