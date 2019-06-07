package model;

import lotto.model.Rank;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RankTest {
    @Test
    void Rank가_MISS인_경우() {
        assertThat(Rank.valueOf(2, false)).isEqualTo(Rank.MISS);
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
    void 유효하지않은_Rank생성시_예외처리() {
        assertThrows(IllegalArgumentException.class, () -> {
            Rank.valueOf(7, false);
        });
    }
}
