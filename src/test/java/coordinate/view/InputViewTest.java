package coordinate.view;

import coordinate.model.AbstractFigure;
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
    void 사용자_입력에_따른_Point_생성() {
        AbstractFigure abstractFigure = InputView.inputCoordinates("(1, 10)");
        assertThat(abstractFigure).isEqualTo(new Point(1, 10));
    }

    @Test
    void 사용자_입력에_따른_Line_생성() {
        AbstractFigure abstractFigure = InputView.inputCoordinates("(1, 1) - (2, 2)");
        points.add(new Point(1, 1));
        points.add(new Point(2, 2));
        assertThat(abstractFigure).isEqualTo(new Line(points));
    }

    @AfterEach
    void tearDown() {
        points = null;
    }
}