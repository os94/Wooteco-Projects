package StringAddCalculator;

public class Calculator {
    public static int add(String s) {
        int result = 0;
        String[] numbers = null;

        if ("".equals(s) || s == null) {
            return 0;
        }

        numbers = s.split(",");

        for (String number : numbers) {
            result += Integer.parseInt(number);
        }

        return result;
    }
}
