package ladderGame.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class GameResultTest {
    GameResult gameResult;

    @BeforeEach
    void setUp() {
        Map<String, String> result = new HashMap<>();
        result.put("pobi", "win");
        result.put("crong", "fail");
        gameResult = new GameResult(result);
    }

    @Test
    void has_member() {
        assertThat(gameResult.has("pobi")).isTrue();
        assertThat(gameResult.has("sean")).isFalse();
    }

    @Test
    void getter() {
        assertThat(gameResult.of("pobi")).isEqualTo("win");
    }
}
