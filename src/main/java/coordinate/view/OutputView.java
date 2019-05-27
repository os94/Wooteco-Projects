package coordinate.view;

import coordinate.Message;
import coordinate.model.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OutputView {
    private static final String FOUR_BLANK = "    ";
    private static final String VERTICAL_AXIS = "|";
    private static final String ORIGIN = "+";
    private static final String HORIZONTAL_AXIS = "----";
    private static final String MARK_OF_POINT = ".";

    private static final List<String> FIGURE_NAMES = Arrays.asList("선", "삼각형", "사각형");
    private static Map<String, String> printClassifier = new HashMap<>();

    static {
        printClassifier.put(FIGURE_NAMES.get(0), Message.OUTPUT_DISTANCE_OF_LINE);
        printClassifier.put(FIGURE_NAMES.get(1), FIGURE_NAMES.get(1) + Message.OUTPUT_AREA);
        printClassifier.put(FIGURE_NAMES.get(2), FIGURE_NAMES.get(2) + Message.OUTPUT_AREA);
    }

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
        for (int x = Point.LOWER_LIMIT; x <= Point.UPPER_LIMIT; x++) {
            if (figure.hasPoint(x, y)) {
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

    public static void showArea(Figure figure) {
        System.out.println(printClassifier.get(figure.getName()) + figure.area());
    }
}
