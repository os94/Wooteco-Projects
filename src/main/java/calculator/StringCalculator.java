/*
 * @class       StringCalculator class
 * @version     1.1.0
 * @date        19.05.13
 * @author      OHSANG SEO (tjdhtkd@gmail.com)
 * @brief       string calculator main business logic
 */

package calculator;

import java.util.*;

public class StringCalculator {
    private final static int INDEX_TWO = 2;

    public int calculate(String value) {
        String[] expression = value.split(" ");

        Queue<Integer> numbers = extractNumbers(expression);
        Queue<String> operators = extractOperators(expression);

        return getResult(numbers, operators);
    }

    private Queue<Integer> extractNumbers(String[] expression) {
        Queue<Integer> numbers = new LinkedList<>();
        for (int i = 0; i < expression.length; i += INDEX_TWO) {
            numbers.add(Integer.parseInt(expression[i]));
        }
        return numbers;
    }

    private Queue<String> extractOperators(String[] expression) {
        Queue<String> operators = new LinkedList<>();
        for (int i = 1; i < expression.length; i += INDEX_TWO) {
            operators.add(expression[i]);
        }
        return operators;
    }

    private int getResult(Queue<Integer> numbers, Queue<String> operators) {
        int result = Operator.calculate(numbers.poll(), numbers.poll(), operators.poll());
        while (!operators.isEmpty()) {
            result = Operator.calculate(result, numbers.poll(), operators.poll());
        }
        return result;
    }
}
