package coordinate.domain.figure;

import coordinate.domain.Point;
import coordinate.domain.figure.*;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FigureFactoryTest {
    @Test
    public void line() {
        List<Point> points = Arrays.asList(
                Point.of(1, 2),
                Point.of(2, 3));

        Figure figure = FigureFactory.getFigure(points);
        assertThat(figure).isInstanceOfAny(Line.class);
        assertThat(figure.getName()).isEqualTo("선");
    }

    @Test
    public void triangle() {
        List<Point> points = Arrays.asList(
                Point.of(1, 1),
                Point.of(4, 1),
                Point.of(1, 4));

        Figure figure = FigureFactory.getFigure(points);
        assertThat(figure).isInstanceOfAny(Triangle.class);
        assertThat(figure.getName()).isEqualTo("삼각형");
    }

    @Test
    public void rectangle() {
        List<Point> points = Arrays.asList(
                Point.of(1, 1),
                Point.of(4, 1),
                Point.of(1, 4),
                Point.of(4, 4));

        Figure figure = FigureFactory.getFigure(points);
        assertThat(figure).isInstanceOfAny(Rectangle.class);
        assertThat(figure.getName()).isEqualTo("사각형");
    }

    @Test
    public void invalid_figure() {
        List<Point> points = Arrays.asList(Point.of(1, 2));
        assertThrows(InvalidFigureException.class, () -> {
            FigureFactory.getFigure(points);
        });
    }
}
