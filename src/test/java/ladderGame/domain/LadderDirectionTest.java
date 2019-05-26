package ladderGame.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LadderDirectionTest {
    Ladder ladder;

    @BeforeEach
    void setUp() {
        ladder = new Ladder(3, 3);
        ladder.connectBridge(0, 0, true);
        ladder.connectBridge(1, 1, true);
    }

    @Test
    void left_canMove() {
        assertThat(LadderDirection.LEFT.canMove(ladder, new Node(0, 0))).isFalse();
        assertThat(LadderDirection.LEFT.canMove(ladder, new Node(0, 1))).isTrue();
        assertThat(LadderDirection.LEFT.canMove(ladder, new Node(0, 2))).isFalse();
    }

    @Test
    void right_canMove() {
        assertThat(LadderDirection.RIGHT.canMove(ladder, new Node(0, 0))).isTrue();
        assertThat(LadderDirection.RIGHT.canMove(ladder, new Node(0, 1))).isFalse();
        assertThat(LadderDirection.RIGHT.canMove(ladder, new Node(0, 2))).isFalse();
    }

    @Test
    void down_canMove() {
        assertThat(LadderDirection.DOWN.canMove(ladder, new Node(0, 0))).isFalse();
        assertThat(LadderDirection.DOWN.canMove(ladder, new Node(0, 1))).isFalse();
        assertThat(LadderDirection.DOWN.canMove(ladder, new Node(0, 2))).isTrue();
    }
}
