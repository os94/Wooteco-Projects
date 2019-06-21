package chess.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DirectionTest {
    @Test
    void SSE_생성() {
        assertThat(Direction.valueOf(1, -2)).isEqualByComparingTo(Direction.SSE);
        assertThat(Direction.valueOf(2, -4)).isEqualByComparingTo(Direction.SSE);
    }

    @Test
    void North_생성() {
        assertThat(Direction.valueOf(0, 1)).isEqualByComparingTo(Direction.NORTH);
        assertThat(Direction.valueOf(0, 4)).isEqualByComparingTo(Direction.NORTH);
    }

    @Test
    void SOUTHWEST_생성() {
        assertThat(Direction.valueOf(-1, -1)).isEqualByComparingTo(Direction.SOUTHWEST);
        assertThat(Direction.valueOf(-3, -3)).isEqualByComparingTo(Direction.SOUTHWEST);
    }

    @Test
    void 유효하지_않은_direction_생성() {
        assertThrows(IllegalArgumentException.class, () -> {
            Direction.valueOf(1, 3);
        });
    }
}
