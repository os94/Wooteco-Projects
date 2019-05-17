package stringAddCalculator;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calculator {
    private static final String COMMA = ",";
    private static final String COLON = ":";
    private static final String REGEX_DELIMITER = "//(.)\n(.*)";

    public static int calculate(String input) {
        String[] numbers;

        if ("".equals(input) || input == null) {
            return 0;
        }

        numbers = getNumbers(input);
        checkNegativeNumber(numbers);

        return add(numbers);
    }

    private static String[] getNumbers(String input) {
        input = replaceCustomDelimiter(input);
        input = replaceColon(input);
        return input.split(COMMA);
    }

    private static int add(String[] numbers) {
        try {
            return Arrays.stream(numbers).mapToInt(Integer::parseInt).sum();
        } catch (RuntimeException e) {
            throw e;
        }
    }

    private static void checkNegativeNumber(String[] numbers) {
        for (String number : numbers) {
            negativeNumberException(number);
        }
    }

    private static void negativeNumberException(String number) {
        if (Integer.parseInt(number) < 0) {
            throw new RuntimeException();
        }
    }

    private static String replaceColon(String input) {
        if (input.contains(COLON)) {
            input = input.replace(COLON, COMMA);
        }
        return input;
    }

    private static String replaceCustomDelimiter(String input) {
        String customDelimiter;
        Matcher matcher = Pattern.compile(REGEX_DELIMITER).matcher(input);

        if (matcher.find()) {
            customDelimiter = matcher.group(1);
            input = matcher.group(2);
            input = input.replace(customDelimiter, COMMA);
        }
        return input;
    }
}
