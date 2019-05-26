package ladderGame.domain;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class LadderResultTest {
    @Test
    void map() {
        Members members = new Members("pobi,crong");
        Goals goals = new Goals("win,fail", 2);

        Map<Integer, Integer> result = new HashMap<>();
        result.put(0, 1);
        result.put(1, 0);
        LadderResult ladderResult = new LadderResult(result);

        Map<String, String> result2 = new HashMap<>();
        result2.put("pobi", "fail");
        result2.put("crong", "win");
        GameResult gameResult = new GameResult(result2);

        assertThat(ladderResult.map(members, goals)).isEqualTo(gameResult);
    }
}
