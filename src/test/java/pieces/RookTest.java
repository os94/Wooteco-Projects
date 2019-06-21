package pieces;

import org.junit.jupiter.api.Test;
import pieces.Piece.Type;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class RookTest extends PieceTest {
    @Test
    public void create() {
        Position position = new Position(1, 1);
        verifyPiece(Rook.createWhite(position), Rook.createBlack(position), Type.ROOK);
    }
    
    @Test
    public void verifyMovePosition_invalid() throws Exception {
        assertThatExceptionOfType(InvalidMovePositionException.class)
            .isThrownBy(() -> {
                Rook rook= Rook.createWhite(new Position("d1"));
                rook.verifyMovePosition(Pawn.createWhite(new Position("e6")));
            });
    }
    
    @Test
    public void verifyMovePosition() throws Exception {
        Rook rook= Rook.createWhite(new Position("d5"));
        rook.verifyMovePosition(Blank.create(new Position("d8")));
        rook.verifyMovePosition(Blank.create(new Position("d1")));
        rook.verifyMovePosition(Blank.create(new Position("a5")));
        rook.verifyMovePosition(Blank.create(new Position("h5")));
    }
}
