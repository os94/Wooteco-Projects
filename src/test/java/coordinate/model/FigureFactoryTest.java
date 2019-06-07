package coordinate.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FigureFactoryTest {
    private List<Point> points;
    private Figure figure;

    @BeforeEach
    void setUp() {
        points = new ArrayList<>();
    }

    @Test
    void null_입력에_대한_예외처리() {
        assertThrows(IllegalArgumentException.class, () -> {
            FigureFactory.create(null);
        });
    }

    @Test
    void Point_중복_입력에_대한_예외처리() {
        points.add(new Point(1, 2));
        points.add(new Point(3, 5));
        points.add(new Point(1, 2));

        assertThrows(IllegalArgumentException.class, () -> {
            FigureFactory.create(points);
        });
    }

    @Test
    void Point_1개_입력에_대한_예외처리() {
        points.add(new Point(1, 2));

        assertThrows(InvalidFigureTypeException.class, () -> {
            FigureFactory.create(points);
        });
    }

    @Test
    void Point_5개_이상_입력에_대한_예외처리() {
        points.add(new Point(1, 2));
        points.add(new Point(3, 5));
        points.add(new Point(5, 6));
        points.add(new Point(7, 9));
        points.add(new Point(11, 13));

        assertThrows(InvalidFigureTypeException.class, () -> {
            FigureFactory.create(points);
        });
    }

    @Test
    void Point가_2개일_경우_Line_생성() {
        points.add(new Point(1, 2));
        points.add(new Point(3, 4));

        figure = FigureFactory.create(points);
        assertThat(figure).isInstanceOfAny(Line.class);
        assertThat(figure.getName()).isEqualTo("선");
        assertThat(figure).isEqualTo(new Line(points));
    }

    @Test
    void Point가_3개일_경우_Triangle_생성() {
        points.add(new Point(1, 2));
        points.add(new Point(3, 4));
        points.add(new Point(4, 7));

        figure = FigureFactory.create(points);
        assertThat(figure).isInstanceOfAny(Triangle.class);
        assertThat(figure.getName()).isEqualTo("삼각형");
        assertThat(figure).isEqualTo(new Triangle(points));
    }

    @Test
    void Point가_4개일_경우_Rectangle_생성() {
        points.add(new Point(1, 2));
        points.add(new Point(3, 4));
        points.add(new Point(1, 4));
        points.add(new Point(3, 2));

        figure = FigureFactory.create(points);
        assertThat(figure).isInstanceOfAny(Rectangle.class);
        assertThat(figure.getName()).isEqualTo("사각형");
        assertThat(figure).isEqualTo(new Rectangle(points));
    }

    @AfterEach
    void tearDown() {
        points = null;
        figure = null;
    }
}