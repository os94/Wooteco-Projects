package chess.model.piece;

import chess.model.Direction;
import chess.model.PlayerType;
import chess.model.Point;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class KnightTest {
    Piece knight;

    @BeforeEach
    void setUp() {
        knight = new Knight(PlayerType.WHITE, Point.of(4, 4));
    }

    @Test
    void 방향_일치() {
        assertThat(knight.canMove(Direction.NNE, Point.of(5, 6))).isTrue();
    }

    @Test
    void 방향_불일치() {
        assertThat(knight.canMove(Direction.NORTH, Point.of(4, 5))).isFalse();
        assertThat(knight.canMove(Direction.NORTHEAST, Point.of(5, 5))).isFalse();
        assertThat(knight.canMove(Direction.EAST, Point.of(6, 4))).isFalse();
    }
}
