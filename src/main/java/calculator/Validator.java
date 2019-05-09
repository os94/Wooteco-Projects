package calculator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
    private final static String REGEX_CALCULATOR_FORMAT = "[0-9]{1,}([\\+|\\-|\\*|\\/][0-9]{1,}){1,}";
    private final static char SIGN_DIVIDE = '/';

    public static boolean isValid(String value) {
        value = value.replace(" ", "");
        return checkFormat(value) && checkDivideZero(value);
    }

    public static boolean checkFormat(String value) {
        Pattern pattern = Pattern.compile(REGEX_CALCULATOR_FORMAT);
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }

    public static boolean checkDivideZero(String values) {
        boolean result = true;
        for (int i = 0; i < values.length(); i++) {
            result = result && checkDivideZero(values, i);
        }
        return result;
    }

    private static boolean checkDivideZero(String values, int i) {
        return (values.charAt(i) == SIGN_DIVIDE && values.charAt(i + 1) == 0);
    }
}
