package coordinate.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.offset;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PointTest {
    @Test
    void 포인트_생성() {
        assertThat(new Point(1, 2)).isEqualTo(new Point(1, 2));
    }

    @Test
    void 좌표의_범위를_넘어가는_값이_생성자에_입력되었을_경우() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Point(0, 1);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Point(1, 0);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Point(25, 1);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Point(1, 25);
        });
    }

    @Test
    void 두_점_사이의_기울기_계산() {
        assertThat(new Point(1, 1).calculateSlope(new Point(3, 4))).isEqualTo(1.5);
    }

    @Test
    void 두_점_사이의_거리_계산() {
        assertThat(new Point(1, 1).calculateDistance(new Point(2, 2))).isEqualTo(1.414, offset(0.00099));
    }
}
