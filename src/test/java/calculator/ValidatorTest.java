package calculator;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ValidatorTest {
    @Test
    void 정상_통과() {
        assertThat(Validator.isValid("10 + 2 / 6")).isTrue();
    }

    @Test
    void 정규식_통과_실패() {
        assertThat(Validator.isRightFormat("+ 3 + 3")).isFalse();
        assertThat(Validator.isRightFormat("10 + * 5")).isFalse();
        assertThat(Validator.isRightFormat("10 ^ 2")).isFalse();
        assertThat(Validator.isRightFormat("2")).isFalse();
    }

    @Test
    void 영으로_나누는_경우() {
        assertThat(Validator.isDivideZero("10 + 2 / 0")).isFalse();
    }
}
