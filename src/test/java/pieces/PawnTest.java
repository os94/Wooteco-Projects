package pieces;

import org.junit.jupiter.api.Test;
import pieces.Piece.Type;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PawnTest extends PieceTest {
    @Test
    public void create() {
        Position position = new Position(1, 1);
        verifyPiece(Pawn.createWhite(position), Pawn.createBlack(position), Type.PAWN);
    }
    
    @Test
    public void isStartingPosition() throws Exception {
        Pawn pawn = Pawn.createWhite(new Position("e2"));
        assertThat(pawn.isStartingPosition()).isTrue();
        pawn = Pawn.createWhite(new Position("e3"));
        assertThat(pawn.isStartingPosition()).isFalse();

        pawn = Pawn.createBlack(new Position("e7"));
        assertThat(pawn.isStartingPosition()).isTrue();
        pawn = Pawn.createBlack(new Position("e6"));
        assertThat(pawn.isStartingPosition()).isFalse();
    }

    @Test
    public void verifyMovePosition_starting() throws Exception {
        Pawn pawn = Pawn.createWhite(new Position("e2"));
        pawn.verifyMovePosition(Blank.create(new Position("e3")));
        pawn.verifyMovePosition(Blank.create(new Position("e4")));
        pawn.verifyMovePosition(Blank.create(new Position("d3")));
        pawn.verifyMovePosition(Blank.create(new Position("f3")));

        pawn = Pawn.createBlack(new Position("e7"));
        pawn.verifyMovePosition(Blank.create(new Position("e6")));
        pawn.verifyMovePosition(Blank.create(new Position("e5")));
        pawn.verifyMovePosition(Blank.create(new Position("d6")));
        pawn.verifyMovePosition(Blank.create(new Position("f6")));
    }

    @Test
    public void verifyMovePosition_non_starting() throws Exception {
        assertThatThrownBy(() -> {
            Pawn pawn = Pawn.createWhite(new Position("e3"));
            pawn.verifyMovePosition(Blank.create(new Position("e5")));
        }).isInstanceOf(InvalidMovePositionException.class);
    }
    
    @Test
    public void verifyMovePosition_starting_overmove() throws Exception {
        assertThatThrownBy(() -> {
            Pawn pawn = Pawn.createWhite(new Position("e2"));
            pawn.verifyMovePosition(Blank.create(new Position("e5")));
        }).isInstanceOf(InvalidMovePositionException.class);
    }

    @Test
    public void verifyMovePosition_invalid_white() throws Exception {
        assertThatThrownBy(() -> {
            Pawn pawn = Pawn.createWhite(new Position("e5"));
            pawn.verifyMovePosition(Blank.create(new Position("e4")));
        }).isInstanceOf(InvalidMovePositionException.class);
    }

    @Test
    public void verifyMovePosition_invalid_black() throws Exception {
        assertThatThrownBy(() -> {
            Pawn pawn = Pawn.createBlack(new Position("e3"));
            pawn.verifyMovePosition(Blank.create(new Position("e4")));
        }).isInstanceOf(InvalidMovePositionException.class);
    }
}
