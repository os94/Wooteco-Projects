package calculator;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ValidatorTest {
    @Test
    void 유효성_검증() {
        assertThat(Validator.isValid("10 + 2 / 6")).isEqualTo(true);

        assertThat(Validator.isRightFormat("+ 3 + 3")).isEqualTo(false);
        assertThat(Validator.isRightFormat("10 + * 5")).isEqualTo(false);
        assertThat(Validator.isRightFormat("10 ^ 2")).isEqualTo(false);
        assertThat(Validator.isRightFormat("2")).isEqualTo(false);

        assertThat(Validator.isDivideZero("10 + 2 / 0")).isEqualTo(false);
    }
}
