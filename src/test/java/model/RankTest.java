package model;

import lotto.model.Rank;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RankTest {
    @Test
    void miss() {
        assertThat(Rank.valueOf(2, false)).isEqualTo(Rank.MISS);
    }

    @Test
    void third() {
        assertThat(Rank.valueOf(5, false)).isEqualTo(Rank.THIRD);
    }

    @Test
    void second() {
        assertThat(Rank.valueOf(5, true)).isEqualTo(Rank.SECOND);
    }

    @Test
    void invalid_rank() {
        assertThrows(IllegalArgumentException.class, () -> {
            Rank.valueOf(7, false);
        });
    }
}
