package coordinate;

import coordinate.domain.figure.FigureFactory;
import coordinate.domain.Point;
import coordinate.domain.figure.InvalidFigureException;
import coordinate.domain.figure.InvalidPositionException;
import coordinate.utils.PointParser;
import coordinate.view.InputView;

import java.util.List;

public class CoordinateApplication {
    public static void main(String[] args) {
        String value = InputView.getPoint();
        List<Point> points = PointParser.parse(value);

        try {
            FigureFactory.getFigure(points);
        } catch (InvalidPositionException e1) {
            System.err.println(e1);
        } catch (InvalidFigureException e2) {
            System.err.println(e2);
        }
    }
}
