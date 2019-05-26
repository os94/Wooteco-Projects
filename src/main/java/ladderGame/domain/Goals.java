package ladderGame.domain;

import ladderGame.constant.Contants;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;

public class Goals {
    private final List<String> goals;

    public Goals(String input, int memberSize) {
        if (StringUtils.isBlank(input)) {
            throw new IllegalArgumentException();
        }
        String[] inputs = input.split(Contants.DELIMITER_COMMA);
        this.goals = Arrays.asList(inputs);
        validateGoals(memberSize);
    }

    private void validateGoals(int memberSize) {
        if (hasBlank()) {
            throw new IllegalArgumentException(Contants.ERROR_INPUT);
        }
        if (misMatchWithMembers(memberSize)) {
            throw new IllegalArgumentException(Contants.ERROR_MISMATCH_MEMBER_AND_GOAL);
        }
    }

    private boolean hasBlank() {
        return goals.contains("");
    }

    private boolean misMatchWithMembers(int memberSize) {
        return goals.size() != memberSize;
    }

    public List<String> getGoals() {
        return goals;
    }

    public String getGoal(int index) {
        return goals.get(index);
    }
}
