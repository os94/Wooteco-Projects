package cal;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
    private final static int START_NUMBER = 0;

    public static boolean isValid(String value) {
        value = value.replace(" ", "");
        return checkFormat(value) && checkDivideZero(value);
    }

    public static boolean checkFormat(String value) {
        Pattern pattern = Pattern.compile("[0-9]{1,}([\\+|\\-|\\*|\\/][0-9]{1,}){1,}");
        Matcher matcher = pattern.matcher(value);

        if (matcher.matches()) {
            return true;
        }
        return false;
    }

    public static boolean checkDivideZero(String values){
        boolean result = true;
        for (int i=0; i<values.length(); i++) {
            result = result && checkZeroAfterDivide(values, i);
        }
        return result;
    }

    private static boolean checkZeroAfterDivide(String values, int i) {
        if (values.charAt(i) == '/' && values.charAt(i+1) == 0) {
            return false;
        }
        return true;
    }
}
