package calculator;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ValidatorTest {
    @Test
    void 유효성_검증() {
        assertThat(Validator.isValid("1 + 2")).isEqualTo(true);
    }
}
