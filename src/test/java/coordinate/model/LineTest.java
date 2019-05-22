package coordinate.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class LineTest {
    private Line line;

    @BeforeEach
    void setUp() {
        List<Point> points = Arrays.asList(new Point(1, 2), new Point(3, 4));
        line = new Line(points);
    }

    @Test
    void 주어진_포인트를_가지고_있는지_확인() {
        assertThat(line.hasPoint(3, 4)).isTrue();
        assertThat(line.hasPoint(3, 5)).isFalse();
    }

    @AfterEach
    void tearDown() {
        line = null;
    }
}
