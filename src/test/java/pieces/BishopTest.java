package pieces;

import org.junit.jupiter.api.Test;
import pieces.Piece.Type;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class BishopTest extends PieceTest {

    @Test
    public void create() {
        Position position = new Position(1, 1);
        verifyPiece(Bishop.createWhite(position), Bishop.createBlack(position), Type.BISHOP);
    }

    @Test
    public void verifyMovePosition_invalid() throws Exception {
        assertThatExceptionOfType(InvalidMovePositionException.class)
                .isThrownBy(() -> {
                    Bishop bishop = Bishop.createWhite(new Position("d5"));
                    Pawn pawn = Pawn.createWhite(new Position("f5"));
                    bishop.verifyMovePosition(pawn);
                });
    }

    @Test
    public void verifyMovePosition_sameTeam() throws Exception {
        assertThatExceptionOfType(InvalidMovePositionException.class)
                .isThrownBy(() -> {
                    Bishop bishop = Bishop.createWhite(new Position("d5"));
                    bishop.verifyMovePosition(Pawn.createWhite(new Position("g8")));
                });
    }

    @Test
    public void verifyMovePosition_otherTeam() throws Exception {
        Bishop bishop = Bishop.createWhite(new Position("d5"));
        bishop.verifyMovePosition(Pawn.createBlack(new Position("g8")));
    }

    @Test
    public void verifyMovePosition() throws Exception {
        Bishop bishop = Bishop.createWhite(new Position("d5"));
        bishop.verifyMovePosition(Blank.create(new Position("g8")));
        bishop.verifyMovePosition(Blank.create(new Position("a2")));
        bishop.verifyMovePosition(Blank.create(new Position("h1")));
        bishop.verifyMovePosition(Blank.create(new Position("a8")));
    }
}
