package calculator;

public class Main {
    public static void main(String[] args) {
        StringCalculator stringCalculator = new StringCalculator();

        String expression = User.input();
        if (Validator.isValid(expression)) {
            stringCalculator.calculate(expression);
        }
    }
}
