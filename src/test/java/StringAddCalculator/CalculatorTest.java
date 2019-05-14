package StringAddCalculator;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CalculatorTest {
    @Test
    void 빈_문자열_또는_NULL_입력() {
        int result = Calculator.add("");
        assertThat(result).isEqualTo(0);

        result = Calculator.add(null);
        assertThat(result).isEqualTo(0);
    }
}
