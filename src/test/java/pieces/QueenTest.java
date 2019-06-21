package pieces;

import org.junit.jupiter.api.Test;
import pieces.Piece.Type;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class QueenTest extends PieceTest {
    @Test
    public void create() {
        Position position = new Position(1, 1);
        verifyPiece(Queen.createWhite(position), Queen.createBlack(position), Type.QUEEN);
    }
    
    @Test
    public void verifyMovePosition_invalid() throws Exception {
        assertThatThrownBy(() -> {
            Queen queen= Queen.createWhite(new Position("d4"));
            queen.verifyMovePosition(Pawn.createWhite(new Position("h2")));
        }).isInstanceOf(InvalidMovePositionException.class);
    }
    
    @Test
    public void verifyMovePosition() throws Exception {
        Queen queen= Queen.createWhite(new Position("d4"));
        queen.verifyMovePosition(Blank.create(new Position("a1")));
        queen.verifyMovePosition(Blank.create(new Position("a7")));
        queen.verifyMovePosition(Blank.create(new Position("h4")));
        queen.verifyMovePosition(Blank.create(new Position("h8")));
        queen.verifyMovePosition(Blank.create(new Position("d1")));
        queen.verifyMovePosition(Blank.create(new Position("d8")));
    }
}
