package coordinate.view;

import coordinate.model.Point;

import java.util.Map;

public class OutputView {
    private static final String FOUR_BLANK = "    ";
    private static final String VERTICAL_AXIS = "|";
    private static final String ORIGIN = "+";
    private static final String HORIZONTAL_AXIS = "----";
    private static final String MARK_OF_POINT = ".";

    public static void showCoordinatePlane(Map<Integer, Integer> xyCoordinates) {
        showVerticalNumbersWith(xyCoordinates);
        showHorizontalAxis();
        showHorizontalNumbers();
    }

    private static void showVerticalNumbersWith(Map<Integer, Integer> xyCoordinates) {
        for (int y = Point.UPPER_LIMIT; y >= Point.LOWER_LIMIT; y--) {
            showAxisNumber(y);
            System.out.print(VERTICAL_AXIS);
            showPoints(xyCoordinates, y);
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

    private static void showPoints(Map<Integer, Integer> xyCoordinates, int y) {
        if (!xyCoordinates.containsValue(y)) {
            return;
        }
        for (int x = Point.LOWER_LIMIT; x <= Point.UPPER_LIMIT; x++) {
            showPointOrBlank(xyCoordinates.getOrDefault(x, 0), y);
        }
    }

    private static void showPointOrBlank(int pointY, int currentY) {
        if (pointY == currentY) {
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

    public static void print(String message) {
        System.out.println(message);
    }
}
