package cal;

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

    private void divideExpression(Queue<Integer> numbers, Queue<String> operators, String[] temp) {
        for (int i = 0; i < temp.length; i++) {
            if (i % 2 == 0) {
                numbers.add(Integer.parseInt(temp[i]));
            }
            if (i % 2 == 1) {
                operators.add(temp[i]);
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
