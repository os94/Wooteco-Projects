package calculator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
    private final static String REGEX_CALCULATOR_FORMAT = "[0-9]{1,}([\\+|\\-|\\*|\\/][0-9]{1,}){1,}";
    private final static char SIGN_DIVIDE = '/';

    public static boolean isValid(String value) {
        value = value.replace(" ", "");
        return isRightFormat(value) && !isDivideZero(value);
    }

    public static boolean isRightFormat(String value) {
        Pattern pattern = Pattern.compile(REGEX_CALCULATOR_FORMAT);
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }

    public static boolean isDivideZero(String values) {
        boolean result = false;
        for (int i = 0; i < values.length(); i++) {
            result = result || isDivideZero(values, i);
        }
        return result;
    }

    private static boolean isDivideZero(String values, int i) {
        return (values.charAt(i) == SIGN_DIVIDE && values.charAt(i + 1) == 0);
    }
}
