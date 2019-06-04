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
        List<String> inputs = Arrays.asList(input.split(Contants.DELIMITER_COMMA));
        validateGoals(inputs, memberSize);
        this.goals = inputs;
    }

    private void validateGoals(List<String> inputs, int memberSize) {
        if (hasBlank(inputs)) {
            throw new IllegalArgumentException(Contants.ERROR_INPUT);
        }
        if (misMatchWithMembers(inputs, memberSize)) {
            throw new IllegalArgumentException(Contants.ERROR_MISMATCH_MEMBER_AND_GOAL);
        }
    }

    private boolean hasBlank(List<String> inputs) {
        return inputs.contains("");
    }

    private boolean misMatchWithMembers(List<String> inputs, int memberSize) {
        return inputs.size() != memberSize;
    }

    public List<String> getGoals() {
        return goals;
    }

    public String getGoal(int index) {
        return goals.get(index);
    }
}
