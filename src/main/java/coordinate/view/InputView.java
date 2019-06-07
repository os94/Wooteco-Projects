package coordinate.view;

import coordinate.Message;
import coordinate.model.Point;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class InputView {
    private static final String POINT_DELIMITER = "-";
    private static Scanner scanner = new Scanner(System.in);
    private static final Pattern PATTERN_MULTIPLE_POINTS = Pattern.compile("(\\([0-9]{1,2},[0-9]{1,2}\\))(-(\\([0-9]{1,2},[0-9]{1,2}\\))){0,3}");
    private static final Pattern PATTERN_POINT = Pattern.compile("\\(([0-9]{1,2}),([0-9]{1,2})\\)");

    public static List<Point> inputCoordinates() {
        System.out.println(Message.INPUT_COORDINATE);
        return inputCoordinates(scanner.nextLine());
    }

    public static List<Point> inputCoordinates(String input) {
        try {
            input = StringUtils.deleteWhitespace(input);
            checkInputPointsFormat(input);
            return generatePoints(input);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return inputCoordinates();
        }
    }

    private static void checkInputPointsFormat(String input) {
        Matcher matcher = PATTERN_MULTIPLE_POINTS.matcher(input);
        if (!matcher.matches()) {
            throw new IllegalArgumentException(Message.ERROR_INVALID_COORDINATES);
        }
    }

    private static List<Point> generatePoints(String input) {
        String[] inputPoints = input.split(POINT_DELIMITER);

        return Arrays.stream(inputPoints)
                .map(InputView::generatePoint)
                .collect(Collectors.toList())
                ;
    }

    private static Point generatePoint(String inputPoint) {
        Matcher matcher = PATTERN_POINT.matcher(inputPoint);
        if (matcher.find()) {
            int x = Integer.parseInt(matcher.group(1));
            int y = Integer.parseInt(matcher.group(2));
            return new Point(x, y);
        }
        throw new IllegalArgumentException(Message.ERROR_INVALID_COORDINATES);
    }
}
