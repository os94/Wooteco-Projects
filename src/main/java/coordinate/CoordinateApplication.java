package coordinate;

import coordinate.domain.figure.FigureFactory;
import coordinate.domain.Point;
import coordinate.utils.PointParser;
import coordinate.view.InputView;

import java.util.List;

public class CoordinateApplication {
    public static void main(String[] args) {
        String value = InputView.getPoint();
        List<Point> points = PointParser.parse(value);
        FigureFactory.getFigure(points);
    }
}
