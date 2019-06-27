package chess.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PointTest {
    @Test
    void 포인트_생성_확인() {
        assertThat(Point.of(1, 1)).isEqualTo(Point.of(1, 1));
        assertThat(Point.of(1, 1) == Point.of(1, 1)).isTrue();
    }

    @Test
    void 거리_계산() {
        assertThat(Point.of(1, 1).calculateDistance(Point.of(2, 2))).isEqualTo(Math.sqrt(2));
    }

    @Test
    void 방향_계산() {
        Point point1 = Point.of(1, 1);
        Point point2 = Point.of(2, 2);

        assertThat(point1.calculateDirection(point2)).isEqualByComparingTo(Direction.NORTHEAST);
    }
}
