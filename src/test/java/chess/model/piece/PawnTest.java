package chess.model.piece;

import chess.model.Direction;
import chess.model.PlayerType;
import chess.model.Point;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PawnTest {
    Piece pawn;

    @BeforeEach
    void setUp() {
        pawn = new Pawn(PlayerType.WHITE, Point.of(3, 2));
    }

    @Test
    void 직진_1칸_이동_성공_방향일치() {
        assertThat(pawn.canMove(Direction.NORTH, Point.of(3, 3))).isTrue();
    }

    @Test
    void 직진_2칸_이동_성공_방향일치() {
        assertThat(pawn.canMove(Direction.NORTH, Point.of(3, 4))).isTrue();
    }

    @Test
    void 직진_1칸_또는_2칸_이동_실패_방향불일치() {
        assertThat(pawn.canMove(Direction.WEST, Point.of(2, 2))).isFalse();
        assertThat(pawn.canMove(Direction.EAST, Point.of(4, 2))).isFalse();
    }

    @Test
    void 직진_2칸_이동_실패_초기위치가_아닌_경우() {
        pawn.move(Point.of(3, 3));
        assertThat(pawn.canMove(Direction.NORTH, Point.of(3, 5))).isFalse();
    }

    @Test
    void 대각선_이동_성공_방향일치() {
        assertThat(pawn.canMove(Direction.NORTHWEST, Point.of(2, 3))).isTrue();
        assertThat(pawn.canMove(Direction.NORTHEAST, Point.of(4, 3))).isTrue();
    }

    @Test
    void 대각선_이동_실패_방향불일치() {
        assertThat(pawn.canMove(Direction.SOUTHWEST, Point.of(2, 1))).isFalse();
        assertThat(pawn.canMove(Direction.SOUTHEAST, Point.of(4, 1))).isFalse();
    }

    @Test
    void Pawn의_이동경로가_아닌_경우() {
        assertThrows(IllegalArgumentException.class, () -> {
            pawn.canMove(Direction.NORTH, Point.of(5, 5));
        });
    }

    @Test
    void 대각선_이동_성공_적_존재하는_경우() {
        assertThat(pawn.isAvailableDestinationOfPawn(Direction.NORTHWEST, new Pawn(PlayerType.BLACK, Point.of(2, 3)))).isTrue();
        assertThat(pawn.isAvailableDestinationOfPawn(Direction.NORTHEAST, new Pawn(PlayerType.BLACK, Point.of(4, 3)))).isTrue();
    }

    @Test
    void 대각선_이동_실패_적_존재하지_않는_경우() {
        assertThat(pawn.isAvailableDestinationOfPawn(Direction.NORTHWEST, new Blank(PlayerType.NONE, Point.of(2, 3)))).isFalse();
        assertThat(pawn.isAvailableDestinationOfPawn(Direction.NORTHEAST, new Blank(PlayerType.NONE, Point.of(4, 3)))).isFalse();
    }

    @Test
    void 직진_1칸_이동_성공_비어있는_경우() {
        assertThat(pawn.isAvailableDestinationOfPawn(Direction.NORTH, new Blank(PlayerType.NONE, Point.of(3, 3)))).isTrue();
    }

    @Test
    void 직진_1칸_이동_실패_적_존재하는_경우() {
        assertThat(pawn.isAvailableDestinationOfPawn(Direction.NORTH, new Pawn(PlayerType.BLACK, Point.of(3, 3)))).isFalse();
    }

    @Test
    void 직진_2칸_이동_성공_비어있는_경우() {
        assertThat(pawn.isAvailableDestinationOfPawn(Direction.NORTH, new Blank(PlayerType.NONE, Point.of(3, 4)))).isTrue();
    }

    @Test
    void 직진_2칸_이동_실패_적_존재하는_경우() {
        assertThat(pawn.isAvailableDestinationOfPawn(Direction.NORTH, new Pawn(PlayerType.BLACK, Point.of(3, 4)))).isFalse();
    }
}
