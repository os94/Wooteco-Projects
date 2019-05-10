/*
 * @class       StringCalculator class
 * @version     1.0.0
 * @date        19.05.10
 * @author      OHSANG SEO (tjdhtkd@gmail.com)
 * @brief       string calculator main business logic
 */

package calculator;

import java.util.LinkedList;
import java.util.Queue;

public class StringCalculator {
    private final static String PLUS = "+";
    private final static String MINUS = "-";
    private final static String MULTIPLE = "*";
    private final static String DIVIDE = "/";

    public int calculate(String value) {
        String[] expression = value.split(" ");
        Queue<Integer> numbers = new LinkedList<>();
        Queue<String> operators = new LinkedList<>();

        divideExpression(numbers, operators, expression);

        return getResult(numbers, operators);
    }

    private void divideExpression(Queue<Integer> numbers, Queue<String> operators, String[] expression) {
        for (int i = 0; i < expression.length; i++) {
            if (i % 2 == 0) { // even
                numbers.add(Integer.parseInt(expression[i]));
            }
            if (i % 2 == 1) { // odd
                operators.add(expression[i]);
            }
        }
    }

    private int getResult(Queue<Integer> numbers, Queue<String> operators) {
        int result = map(numbers.poll(), numbers.poll(), operators.poll());
        while (!operators.isEmpty()) {
            result = map(result, numbers.poll(), operators.poll());
        }
        return result;
    }

    private int map(int i, int j, String operator) {
        if (operator.equals(PLUS)) {
            return Calculator.add(i, j);
        }
        if (operator.equals(MINUS)) {
            return Calculator.subtract(i, j);
        }
        if (operator.equals(MULTIPLE)) {
            return Calculator.multiply(i, j);
        }
        if (operator.equals(DIVIDE)) {
            return Calculator.divide(i, j);
        }
        throw new IllegalArgumentException();
    }
}
