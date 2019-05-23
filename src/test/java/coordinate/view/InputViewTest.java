package coordinate.view;

import coordinate.model.Figure;
import coordinate.model.Line;
import coordinate.model.Point;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class InputViewTest {
    private List<Point> points;

    @BeforeEach
    void setUp() {
        points = new ArrayList<>();
    }

    @Test
    void 사용자_입력에_따른_Line_생성() {
        Figure figure = InputView.inputCoordinates("(1, 1) - (2, 2)");
        points.add(new Point(1, 1));
        points.add(new Point(2, 2));
        assertThat(figure).isEqualTo(new Line(points));
    }

    @AfterEach
    void tearDown() {
        points = null;
    }
}