/*
 * @class       Operator enum
 * @version     1.1.0
 * @date        19.05.13
 * @author      OHSANG SEO (tjdhtkd@gmail.com)
 * @brief       mean 4 operators in calculator
 */

package calculator;

import java.util.function.BiFunction;

public enum Operator {
    PLUS("+", (num1, num2) -> num1 + num2),
    MINUS("-", (num1, num2) -> num1 - num2),
    MULTIPLY("*", (num1, num2) -> num1 * num2),
    DIVIDE("/", (num1, num2) -> num1 / num2);

    private String symbol;
    private BiFunction<Integer, Integer, Integer> expression;

    Operator(String symbol, BiFunction<Integer, Integer, Integer> expression) {
        this.symbol = symbol;
        this.expression = expression;
    }

    public static int calculate(int num1, int num2, String inputSymbol) {
        for (Operator operator : Operator.values()) {
            if (operator.match(inputSymbol)) {
                return operator.expression.apply(num1, num2);
            }
        }
        throw new IllegalArgumentException();
    }

    private boolean match(String inputSymbol) {
        return this.symbol.equals(inputSymbol);
    }
}
