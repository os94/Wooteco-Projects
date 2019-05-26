package ladderGame.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class LadderTest {
    Ladder ladder;

    @BeforeEach
    void setUp() {
        ladder = new Ladder(3, 3);
        ladder.connectBridge(0, 0, true);
        ladder.connectBridge(1, 1, true);
    }

    @Test
    void play() {
        Map<Integer, Integer> result = new HashMap<>();
        result.put(0, 2);
        result.put(1, 0);
        result.put(2, 1);
        LadderResult ladderResult = new LadderResult(result);

        assertThat(ladder.play()).isEqualTo(ladderResult);
    }

    @Test
    void getNumberOfColumn() {
        assertThat(ladder.getNumberOfColumn()).isEqualTo(3);
    }

    @Test
    void isConnected() {
        assertThat(ladder.isConnected(0, 0)).isTrue();
        assertThat(ladder.isConnected(0, 1)).isFalse();
    }

    @Test
    void atDestination() {
        assertThat(ladder.atDestination(new Node(4, 0))).isTrue();
        assertThat(ladder.atDestination(new Node(1, 0))).isFalse();
    }

    @Test
    void atFirstColumn() {
        assertThat(ladder.atFirstColumn(new Node(0, 0))).isTrue();
        assertThat(ladder.atFirstColumn(new Node(0, 1))).isFalse();
    }

    @Test
    void atLastColumn() {
        assertThat(ladder.atLastColumn(new Node(0, 2))).isTrue();
        assertThat(ladder.atLastColumn(new Node(0, 1))).isFalse();
    }
}
