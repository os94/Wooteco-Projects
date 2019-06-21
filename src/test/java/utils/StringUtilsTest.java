package utils;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StringUtilsTest {
    @Test
    public void appendNewLine() {
        String line = "new line";
        String result = StringUtils.appendNewLine(line);
        assertThat(result).isEqualTo(line + StringUtils.NEWLINE);
    }
}
