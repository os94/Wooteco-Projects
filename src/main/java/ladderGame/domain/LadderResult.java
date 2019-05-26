package ladderGame.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class LadderResult {
    private final Map<Integer, Integer> ladderResult;

    public LadderResult(Map<Integer, Integer> ladderResult) {
        this.ladderResult = ladderResult;
    }

    public GameResult map(Members members, Goals goals) {
        Map<String, String> result = new HashMap<>();

        for (int index = 0; index < ladderResult.size(); index++) {
            result.put(members.getMember(index), goals.getGoal(ladderResult.get(index)));
        }
        return new GameResult(result);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LadderResult that = (LadderResult) o;
        return Objects.equals(ladderResult, that.ladderResult);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ladderResult);
    }
}
