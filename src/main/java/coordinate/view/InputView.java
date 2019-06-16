package coordinate.view;

import coordinate.Message;
import org.apache.commons.lang3.StringUtils;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputView {
    private static Scanner scanner = new Scanner(System.in);
    private static final Pattern PATTERN_MULTIPLE_POINTS = Pattern.compile("(\\([0-9]{1,2},[0-9]{1,2}\\))(-(\\([0-9]{1,2},[0-9]{1,2}\\))){0,3}");

    public static String inputCoordinates() {
        System.out.println(Message.INPUT_COORDINATE);
        try {
            String input = StringUtils.deleteWhitespace(scanner.nextLine());
            checkInputPointsFormat(input);
            return input;
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
}
