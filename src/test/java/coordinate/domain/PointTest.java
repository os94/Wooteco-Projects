package coordinate.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PointTest {
    @Test
    public void 생성() {
        Point a = Point.ofCommaSeparator("1,2");
        assertThat(a).isEqualTo(Point.of(1, 2));
    }
}
