package pieces;

import org.junit.jupiter.api.Test;
import pieces.Piece.Type;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class BlankTest {

    @Test
    public void create() {
        Position position = new Position(1, 1);
        Blank blank = Blank.create(position);
        assertThat(blank.isWhite()).isFalse();
        assertThat(blank.isBlack()).isFalse();
        assertThat(blank.getType()).isEqualTo(Type.NO_PIECE);
    }

    @Test
    public void verifyMovePosition() throws Exception {
        assertThatExceptionOfType(InvalidMovePositionException.class)
                .isThrownBy(() -> {
                    Blank blank = Blank.create(new Position("b3"));
                    blank.verifyMovePosition(Blank.create(new Position("b4")));
                });
    }
}
