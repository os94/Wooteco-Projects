package coordinate.view;

import coordinate.Message;
import coordinate.model.Figure;
import coordinate.model.FigureFactory;
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
    private static final Pattern PATTERN_MULTIPLE_POINTS = Pattern.compile("(\\([0-9]{1,2},[0-9]{1,2}\\))(-(\\([0-9]{1,2},[0-9]{1,2}\\))){0,3}");
    private static final Pattern PATTERN_POINT = Pattern.compile("\\(([0-9]{1,2}),([0-9]{1,2})\\)");
    private static FigureFactory figureFactory = new FigureFactory();

    public static Figure inputCoordinates() {
        System.out.println(Message.INPUT_COORDINATE);
        return inputCoordinates(scanner.nextLine());
    }

    public static Figure inputCoordinates(String input) {
        try {
            input = input.replace(" ", "");
            checkInputPointsFormat(input);
            List<Point> points = generatePoints(input);
            return figureFactory.create(points);
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

        List<Point> points = new ArrayList<>();
        for (String inputPoint : inputPoints) {
            points.add(generatePoint(inputPoint));
        }
        checkDuplicationOf(points);
        return points;
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

    private static void checkDuplicationOf(List<Point> points) {
        if (points.size() != new HashSet<>(points).size()) {
            throw new IllegalArgumentException(Message.ERROR_DUPLICATE_POINTS);
        }
    }
}
