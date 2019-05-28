package coordinate.domain;

import coordinate.domain.figure.InvalidPositionException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PointTest {
    @Test
    public void 생성() {
        Point a = Point.ofCommaSeparator("1,2");
        assertThat(a).isEqualTo(Point.of(1, 2));
    }

    @Test
    void invalid_point_test() {
        assertThrows(InvalidPositionException.class, () -> {
            Point a = Point.ofCommaSeparator("1,100");
            Point b = Point.ofCommaSeparator("-100,1");
        });
    }
}
