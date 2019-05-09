package calculator;

import java.util.LinkedList;
import java.util.Queue;

public class StringCalculator {
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
        if (operator.equals("+")) {
            return Calculator.add(i, j);
        }
        if (operator.equals("-")) {
            return Calculator.subtract(i, j);
        }
        if (operator.equals("*")) {
            return Calculator.multiply(i, j);
        }
        if (operator.equals("/")) {
            return Calculator.divide(i, j);
        }
        throw new IllegalArgumentException();
    }
}
