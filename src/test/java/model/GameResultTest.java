package model;

import lotto.model.GameResult;
import lotto.model.Rank;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GameResultTest {
    GameResult gameResult;

    @BeforeEach
    void setUp() {
        gameResult = new GameResult();
        gameResult.add(Rank.SECOND);
    }

    @Test
    void add_get() {
        assertThat(gameResult.get(Rank.SECOND)).isEqualTo(1);
        assertThat(gameResult.get(Rank.FIRST)).isEqualTo(0);
    }

    @Test
    void getTotalPrizeMoney() {
        assertThat(gameResult.getTotalPrizeMoney()).isEqualTo(30_000_000);
    }

    @AfterEach
    void tearDown() {
        gameResult = null;
    }
}
