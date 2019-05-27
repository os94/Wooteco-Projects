package coordinate.utils;

import coordinate.domain.Point;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class PointParserTest {
    @Test
    public void 점하나() {
        List<Point> points = PointParser.parse("(1,2)");
        assertThat(points).hasSize(1);
        assertThat(points.contains(Point.of(1, 2))).isTrue();
    }

    @Test
    public void 점둘() {
        List<Point> points = PointParser.parse("(1,2)-(3,4)");
        assertThat(points).hasSize(2);
        assertThat(points).containsExactly(Point.of(1, 2), Point.of(3, 4));
    }
}
