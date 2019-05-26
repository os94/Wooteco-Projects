package ladderGame.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RowTest {
    Row row;

    @BeforeEach
    void setUp() {
        row = new Row(3);
        row.connectBridge(0, true);
    }

    @Test
    void isConnected() {
        assertThat(row.isConnected(0)).isTrue();
        assertThat(row.isConnected(1)).isFalse();
    }
}
