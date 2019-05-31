package model;

import lotto.model.GameResult;
import lotto.model.Rank;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GameResultTest {
    @Test
    void add_get() {
        GameResult gameResult = new GameResult();
        gameResult.add(Rank.SECOND);

        assertThat(gameResult.get(Rank.SECOND)).isEqualTo(1);
        assertThat(gameResult.get(Rank.FIRST)).isEqualTo(0);
    }
}
