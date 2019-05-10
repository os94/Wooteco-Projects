/*
 * @class       Main class
 * @version     1.0.0
 * @date        19.05.10
 * @author      OHSANG SEO (tjdhtkd@gmail.com)
 * @brief       main calculator console
 */

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
