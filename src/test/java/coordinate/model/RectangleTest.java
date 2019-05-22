package coordinate.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RectangleTest {
    private List<Point> points;

    @BeforeEach
    void setUp() {
        points = new ArrayList<>();
    }

    @Test
    void 직사각형_생성() {
        points = Arrays.asList(new Point(1, 1), new Point(1, 3), new Point(3, 1), new Point(3, 3));
        Rectangle rectangle = new Rectangle(points);
        assertThat(rectangle).isEqualTo(new Rectangle(points));
    }

    @Test
    void 직사각형_생성_오류() {
        points = Arrays.asList(new Point(1, 1), new Point(1, 3), new Point(3, 1), new Point(3, 5));
        assertThrows(IllegalArgumentException.class, () -> {
            new Rectangle(points);
        });
    }

    @Test
    void 직사각형_넓이_계산() {
        points = Arrays.asList(new Point(1, 1), new Point(1, 15), new Point(3, 1), new Point(3, 15));
        Rectangle rectangle = new Rectangle(points);
        assertThat(rectangle.area()).isEqualTo(28);
    }

    @AfterEach
    void tearDown() {
        points = null;
    }
}
