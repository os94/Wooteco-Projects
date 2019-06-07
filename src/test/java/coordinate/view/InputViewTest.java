package coordinate.view;

import coordinate.model.Figure;
import coordinate.model.FigureFactory;
import coordinate.model.Line;
import coordinate.model.Point;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class InputViewTest {
    @Test
    void 사용자_입력에_따른_Line_생성() {
        List<Point> points = InputView.inputCoordinates("(1, 1) - (2, 2)");
        Figure figure = FigureFactory.create(points);

        assertThat(figure).isInstanceOfAny(Line.class);
        assertThat(figure.getName()).isEqualTo("선");
    }
}