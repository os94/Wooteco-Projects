package coordinate.controller;

import coordinate.Message;
import coordinate.model.Figure;
import coordinate.model.FigureFactory;
import coordinate.model.Point;
import coordinate.view.InputView;
import coordinate.view.OutputView;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class CoordinateCalculator {
    private static final Pattern PATTERN_POINT = Pattern.compile("\\(([0-9]{1,2}),([0-9]{1,2})\\)");
    private static final String POINT_DELIMITER = "-";

    public void run() {
        try {
            String inputPoints = InputView.inputCoordinates();
            Figure figure = FigureFactory.create(generatePoints(inputPoints));
            OutputView.showCoordinatePlane(figure.getXYCoordinates());
            OutputView.print(figure.getAreaInfo());
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    private List<Point> generatePoints(String input) {
        String[] inputPoints = input.split(POINT_DELIMITER);

        return Arrays.stream(inputPoints)
                .map(this::generatePoint)
                .collect(Collectors.toList())
                ;
    }

    private Point generatePoint(String inputPoint) {
        Matcher matcher = PATTERN_POINT.matcher(inputPoint);
        if (matcher.find()) {
            int x = Integer.parseInt(matcher.group(1));
            int y = Integer.parseInt(matcher.group(2));
            return new Point(x, y);
        }
        throw new IllegalArgumentException(Message.ERROR_INVALID_COORDINATES);
    }
}
