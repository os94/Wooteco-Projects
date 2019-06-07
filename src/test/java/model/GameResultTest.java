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
    void 게임결과를_올바르게_가져오는지_확인() {
        assertThat(gameResult.get(Rank.SECOND)).isEqualTo(1);
        assertThat(gameResult.get(Rank.FIRST)).isEqualTo(0);
    }

    @Test
    void 게임결과로부터_합산한_우승상금_확인() {
        assertThat(gameResult.getTotalPrizeMoney()).isEqualTo(30_000_000);
    }

    @AfterEach
    void tearDown() {
        gameResult = null;
    }
}
