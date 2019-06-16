package coordinate.view;

import coordinate.model.Figure;
import coordinate.model.Point;

public class OutputView {
    private static final String FOUR_BLANK = "    ";
    private static final String VERTICAL_AXIS = "|";
    private static final String ORIGIN = "+";
    private static final String HORIZONTAL_AXIS = "----";
    private static final String MARK_OF_POINT = ".";
    private static final int FIRST = 1;

    public static void showCoordinatePlane(Figure figure) {
        showVerticalNumbersWith(figure);
        showHorizontalAxis();
        showHorizontalNumbers();
    }

    private static void showVerticalNumbersWith(Figure figure) {
        for (int y = Point.UPPER_LIMIT; y >= Point.LOWER_LIMIT; y--) {
            showAxisNumber(y);
            System.out.print(VERTICAL_AXIS);
            showPoints(figure, y);
            emptyLine();
        }
    }

    private static void showAxisNumber(int index) {
        if (index % 2 == 0) {
            System.out.print(String.format("%4d", index));
            return;
        }
        System.out.print(FOUR_BLANK);
    }

    private static void showPoints(Figure figure, int y) {
        Point currentPoint = new Point(FIRST, y);
        while (currentPoint.hasNext()) {
            showPointOrBlank(figure, currentPoint);
            currentPoint = currentPoint.right();
        }
        showPointOrBlank(figure, currentPoint);
    }

    private static void showPointOrBlank(Figure figure, Point point) {
        if (figure.hasPoint(point)) {
            System.out.print(String.format("%4s", MARK_OF_POINT));
            return;
        }
        System.out.print(FOUR_BLANK);
    }

    private static void showHorizontalAxis() {
        System.out.print(FOUR_BLANK + ORIGIN);
        for (int x = Point.LOWER_LIMIT; x <= Point.UPPER_LIMIT; x++) {
            System.out.print(HORIZONTAL_AXIS);
        }
        emptyLine();
    }

    private static void showHorizontalNumbers() {
        for (int x = 0; x <= Point.UPPER_LIMIT; x++) {
            showAxisNumber(x);
        }
        emptyLine();
    }

    private static void emptyLine() {
        System.out.println();
    }

    public static void showArea(Figure figure) {
        System.out.println(figure.getAreaInfo());
    }
}
