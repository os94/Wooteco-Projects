package chess.model.piece;

import chess.model.Direction;
import chess.model.PlayerType;
import chess.model.Point;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RookTest {
    Piece rook;

    @BeforeEach
    void setUp() {
        rook = new Rook(PlayerType.WHITE, Point.of(4, 5));
    }

    @Test
    void 방향_일치() {
        assertThat(rook.canMove(Direction.NORTH, Point.of(4, 6))).isTrue();
    }

    @Test
    void 방향_불일치() {
        assertThat(rook.canMove(Direction.NORTHEAST, Point.of(5, 6))).isFalse();
        assertThat(rook.canMove(Direction.NNE, Point.of(5, 7))).isFalse();
    }
}
