package coordinate.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RectangleTest {
    private List<Point> points;
    private Rectangle rectangle;

    @BeforeEach
    void setUp() {
        points = Arrays.asList(new Point(1, 1), new Point(1, 15)
                , new Point(3, 1), new Point(3, 15));
        rectangle = new Rectangle(points);
    }

    @Test
    void 직사각형_생성() {
        assertThat(rectangle).isEqualTo(new Rectangle(points));
    }

    @Test
    void 직사각형_생성_오류() {
        List<Point> illegalPoints = Arrays.asList(new Point(1, 1), new Point(1, 3)
                , new Point(3, 1), new Point(3, 5));
        assertThrows(IllegalArgumentException.class, () -> {
            new Rectangle(illegalPoints);
        });
    }

    @Test
    void 주어진_포인트를_가지고_있는지_확인() {
        assertThat(rectangle.hasPoint(3, 1)).isTrue();
        assertThat(rectangle.hasPoint(3, 5)).isFalse();
    }

    @Test
    void 직사각형_넓이_계산() {
        assertThat(rectangle.area()).isEqualTo(28);
    }

    @AfterEach
    void tearDown() {
        points = null;
        rectangle = null;
    }
}
