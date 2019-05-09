package calculator;

public class Main {
    public static void main(String[] args) {
        StringCalculator stringCalculator = new StringCalculator();
        int result;

        String expression = View.input();
        if (Validator.isValid(expression)) {
            result = stringCalculator.calculate(expression);
            View.output(result);
        }
    }
}
