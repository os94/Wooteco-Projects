package coordinate.view;

import coordinate.Message;
import coordinate.model.AbstractFigure;
import coordinate.model.Line;
import coordinate.model.Point;
import coordinate.model.Rectangle;

public class OutputView {
    private static final String FOUR_BLANK = "    ";
    private static final String VERTICAL_AXIS = "|";
    private static final String ORIGIN = "+";
    private static final String HORIZONTAL_AXIS = "----";
    private static final String MARK_OF_POINT = ".";

    public static void showCoordinatePlane(AbstractFigure abstractFigure) {
        showVerticalNumbersWith(abstractFigure);
        showHorizontalAxis();
        showHorizontalNumbers();
    }

    private static void showVerticalNumbersWith(AbstractFigure abstractFigure) {
        for (int y = Point.UPPER_LIMIT; y >= Point.LOWER_LIMIT; y--) {
            showAxisNumber(y);
            System.out.print(VERTICAL_AXIS);
            showPoints(abstractFigure, y);
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

    private static void showPoints(AbstractFigure abstractFigure, int y) {
        for (int x = Point.LOWER_LIMIT; x <= Point.UPPER_LIMIT; x++) {
            if (abstractFigure.hasPoint(x, y)) {
                System.out.print(String.format("%4s", MARK_OF_POINT));
                continue;
            }
            System.out.print(FOUR_BLANK);
        }
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

    public static void showArea(AbstractFigure abstractFigure) {
        if (abstractFigure instanceof Point) {
            return;
        }
        if (abstractFigure instanceof Line) {
            System.out.println(Message.OUTPUT_AREA_OF_LINE + abstractFigure.area());
        }
        if (abstractFigure instanceof Rectangle) {
            System.out.println(Message.OUTPUT_AREA_OF_RECTANGLE + abstractFigure.area());
        }
    }
}
