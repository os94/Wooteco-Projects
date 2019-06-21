package chess.model.piece;

import chess.model.Direction;
import chess.model.PlayerType;
import chess.model.Point;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class QueenTest {
    Piece queen;

    @BeforeEach
    void setUp() {
        queen = new Queen(PlayerType.WHITE, Point.of(4, 4));
    }

    @Test
    void 방향_일치() {
        assertThat(queen.canMove(Direction.NORTH, Point.of(4, 7))).isTrue();
        assertThat(queen.canMove(Direction.NORTHEAST, Point.of(6, 6))).isTrue();
    }

    @Test
    void 방향_불일치() {
        assertThat(queen.canMove(Direction.NNE, Point.of(5, 6))).isFalse();
    }
}
