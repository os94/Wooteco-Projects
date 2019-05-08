package cal;

public class Main {

    public static void main(String[] args) {
        StringCalculator cal = new StringCalculator();

        String expression = User.input();
        if (Validator.isValid(expression)) {
            cal.calculate(expression);
        }
    }
}
