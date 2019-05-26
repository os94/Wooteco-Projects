package ladderGame.domain;

import ladderGame.constant.Contants;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class Members {
    private List<String> members;

    public Members(String input) {
        if (StringUtils.isBlank(input)) {
            throw new IllegalArgumentException();
        }
        String[] inputs = input.split(Contants.DELIMITER_COMMA);
        this.members = Arrays.asList(inputs);
        validateMembers();
    }

    private void validateMembers() {
        if (hasBlank()) {
            throw new IllegalArgumentException(Contants.ERROR_INPUT);
        }
        if (hasDuplicateName()) {
            throw new IllegalArgumentException(Contants.ERROR_DUPLICATE_NAME);
        }
    }

    private boolean hasBlank() {
        return members.contains("");
    }

    private boolean hasDuplicateName() {
        return members.size() != new HashSet<>(members).size();
    }

    public List<String> getMembers() {
        return members;
    }

    public String getMember(int index) {
        return members.get(index);
    }

    public int getIndexOf(String member) {
        return member.indexOf(member);
    }

    public boolean has(String target) {
        return members.stream().anyMatch(member -> member.equals(target));
    }

    public int size() {
        return members.size();
    }
}
