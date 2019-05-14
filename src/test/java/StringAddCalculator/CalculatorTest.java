package StringAddCalculator;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CalculatorTest {
    @Test
    void 빈_문자열_또는_NULL_입력() {
        int result = Calculator.add("");
        assertThat(result).isEqualTo(0);

        result = Calculator.add(null);
        assertThat(result).isEqualTo(0);
    }

    @Test
    void 숫자_하나() {
        int result = Calculator.add("3");
        assertThat(result).isEqualTo(3);
    }

    @Test
    void 쉼표구분자() {
        int result = Calculator.add("1,2");
        assertThat(result).isEqualTo(3);
    }

    @Test
    void 쉼표_또는_콜론_구분자() {
        int result = Calculator.add("1:2");
        assertThat(result).isEqualTo(3);

        result = Calculator.add("1,2:3");
        assertThat(result).isEqualTo(6);
    }

    @Test
    void 커스텀_구분자_지정() {
        int result = Calculator.add("//;\n1;2;3");
        assertThat(result).isEqualTo(6);
    }
}
