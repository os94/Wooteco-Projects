package stringAddCalculator;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CalculatorTest {
    int result;

    @Test
    void 빈_문자열_또는_NULL_입력() {
        result = Calculator.calculate("");
        assertThat(result).isEqualTo(0);

        result = Calculator.calculate(null);
        assertThat(result).isEqualTo(0);
    }

    @Test
    void 숫자_하나() {
        result = Calculator.calculate("3");
        assertThat(result).isEqualTo(3);
    }

    @Test
    void 쉼표_구분자() {
        result = Calculator.calculate("1,2");
        assertThat(result).isEqualTo(3);
    }

    @Test
    void 쉼표_또는_콜론_구분자() {
        result = Calculator.calculate("1:2");
        assertThat(result).isEqualTo(3);

        result = Calculator.calculate("1,2:3");
        assertThat(result).isEqualTo(6);
    }

    @Test
    void 커스텀_구분자() {
        result = Calculator.calculate("//;\n1;2;3");
        assertThat(result).isEqualTo(6);
    }

    @Test
    void 음수가_들어왔을때() {
        assertThrows(RuntimeException.class, () -> {
            Calculator.calculate("-1,2,3");
        });
    }

    @Test
    void 숫자가_아닌_수가_들어왔을때() {
        assertThrows(RuntimeException.class, () -> {
            Calculator.calculate("a,2,3");
        });
    }
}
