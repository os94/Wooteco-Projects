package coordinate.view;

import coordinate.Message;
import coordinate.model.AbstractFigure;
import coordinate.model.Point;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputView {
    private static final String POINT_DELIMITER = "-";
    private static Scanner scanner = new Scanner(System.in);

    public static AbstractFigure inputCoordinates() {
        System.out.println(Message.INPUT_COORDINATE);
        return inputCoordinates(scanner.nextLine());
    }

    public static AbstractFigure inputCoordinates(String input) {
        try {
            input = input.replace(" ", "");
            checkAccuracyOfPoints(input);
            List<Point> points = generatePoints(input);
            return AbstractFigure.create(points);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return inputCoordinates();
        }
    }

    private static void checkAccuracyOfPoints(String input) {
        Pattern pattern = Pattern.compile("(\\([0-9]{1,2},[0-9]{1,2}\\))(-(\\([0-9]{1,2},[0-9]{1,2}\\))){0,3}");
        Matcher matcher = pattern.matcher(input);
        if (!matcher.matches()) {
            throw new IllegalArgumentException(Message.ERROR_INVALID_COORDINATES);
        }
    }

    private static List<Point> generatePoints(String input) {
        String[] inputPoints = input.split(POINT_DELIMITER);

        List<Point> points = new ArrayList<>();
        for (String inputPoint : inputPoints) {
            points.add(generatePoint(inputPoint));
        }
        checkDuplicationOf(points);
        return points;
    }

    private static Point generatePoint(String inputPoint) {
        Pattern pattern = Pattern.compile("\\(([0-9]{1,2}),([0-9]{1,2})\\)");
        Matcher matcher = pattern.matcher(inputPoint);
        if (matcher.find()) {
            int x = Integer.parseInt(matcher.group(1));
            int y = Integer.parseInt(matcher.group(2));
            return new Point(x, y);
        }
        throw new IllegalArgumentException(Message.ERROR_INVALID_COORDINATES);
    }

    private static void checkDuplicationOf(List<Point> points) {
        if (points.size() != new HashSet<>(points).size()) {
            throw new IllegalArgumentException(Message.ERROR_DUPLICATE_POINTS);
        }
    }
}
