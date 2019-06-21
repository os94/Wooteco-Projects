package chess.model.piece;

import chess.model.Direction;
import chess.model.PlayerType;
import chess.model.Point;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BishopTest {
    Piece bishop;

    @BeforeEach
    void setUp() {
        bishop = new Bishop(PlayerType.WHITE, Point.of(4, 5));
    }

    @Test
    void 방향_일치() {
        assertThat(bishop.canMove(Direction.NORTHEAST, Point.of(5, 6))).isTrue();
    }

    @Test
    void 방향_불일치() {
        assertThat(bishop.canMove(Direction.NORTH, Point.of(4, 6))).isFalse();
        assertThat(bishop.canMove(Direction.NNE, Point.of(5, 7))).isFalse();
    }
}
