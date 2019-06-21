package chess.model.piece;

import chess.model.Direction;
import chess.model.PlayerType;
import chess.model.Point;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class KingTest {
    Piece king;

    @BeforeEach
    void setUp() {
        king = new King(PlayerType.WHITE, Point.of(6, 5));
    }

    @Test
    void 이동_가능_거리_1() {
        assertThat(king.canMove(Direction.NORTH, Point.of(6, 6))).isTrue();
    }

    @Test
    void 이동_가능_거리_root2() {
        assertThat(king.canMove(Direction.NORTHEAST, Point.of(7, 6))).isTrue();
    }

    @Test
    void 이동_불가능_거리() {
        assertThat(king.canMove(Direction.NORTHEAST, Point.of(8, 7))).isFalse();
    }

    @Test
    void 이동_불가능_방향() {
        assertThat(king.canMove(Direction.NNE, Point.of(7, 7))).isFalse();
    }
}
