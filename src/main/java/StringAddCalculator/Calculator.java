package StringAddCalculator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calculator {
    public static int add(String s) {
        int result = 0;
        String[] numbers;
        String customDelimiter;

        if ("".equals(s) || s == null) {
            return 0;
        }

        Matcher matcher = Pattern.compile("//(.)\n(.*)").matcher(s);

        if (matcher.find()) {
            customDelimiter = matcher.group(1);
            s = matcher.group(2);
            s = s.replace(customDelimiter, ",");
        }

        if (s.contains(":")) {
            s = s.replace(":", ",");
        }

        numbers = s.split(",");

        for (String number : numbers) {
            if (Integer.parseInt(number) < 0) {
                throw new RuntimeException();
            }
        }

        for (String number : numbers) {
            result += Integer.parseInt(number);
        }

        return result;
    }
}
