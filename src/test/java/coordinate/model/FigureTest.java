package coordinate.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FigureTest {
    List<Point> points;

    @BeforeEach
    void setUp() {
        points = new ArrayList<>();
    }

    @Test
    void 잘못된_Point_입력에_대한_예외처리() {
        assertThrows(IllegalArgumentException.class, () -> {
            Figure.create(null);
        });

        points.add(new Point(1, 2));
        points.add(new Point(3, 4));
        points.add(new Point(5, 6));
        assertThrows(IllegalArgumentException.class, () -> {
            Figure.create(points);
        });
    }

    @Test
    void Point가_1개일_경우_Point_생성() {
        points.add(new Point(1, 2));
        assertThat(Figure.create(points)).isEqualTo(new Point(1, 2));
    }

    @Test
    void Point가_2개일_경우_Line_생성() {
        points.add(new Point(1, 2));
        points.add(new Point(3, 4));
        assertThat(Figure.create(points)).isEqualTo(new Line(points));
    }

    @AfterEach
    void tearDown() {
        points = null;
    }
}