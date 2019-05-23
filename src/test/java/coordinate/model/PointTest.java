package coordinate.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
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
    void 주어진_좌표와_포인트를_비교() {
        assertThat(new Point(1, 2).isSame(1, 2)).isTrue();
        assertThat(new Point(1, 2).isSame(1, 3)).isFalse();
    }
}
