package model;

import lotto.model.Rank;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RankTest {
    @Test
    void Rank가_MISS인_경우() {
        assertThat(Rank.valueOf(2, false)).isEqualTo(Rank.MISS);
    }

    @Test
    void Rank가_FIFTH인_경우() {
        assertThat(Rank.valueOf(3, false)).isEqualTo(Rank.FIFTH);
    }

    @Test
    void Rank가_FOURTH인_경우() {
        assertThat(Rank.valueOf(4, false)).isEqualTo(Rank.FOURTH);
    }

    @Test
    void Rank가_THIRD인_경우() {
        assertThat(Rank.valueOf(5, false)).isEqualTo(Rank.THIRD);
    }

    @Test
    void Rank가_SECOND인_경우() {
        assertThat(Rank.valueOf(5, true)).isEqualTo(Rank.SECOND);
    }

    @Test
    void Rank가_FIRST인_경우() {
        assertThat(Rank.valueOf(6, false)).isEqualTo(Rank.FIRST);
    }
}
