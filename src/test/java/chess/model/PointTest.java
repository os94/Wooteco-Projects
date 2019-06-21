package chess.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PointTest {
    @Test
    void 거리_계산() {
        assertThat(Point.of(1, 1).calculateDistance(Point.of(2, 2))).isEqualTo(Math.sqrt(2));
    }
}
