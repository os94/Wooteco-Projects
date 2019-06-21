package pieces;

import org.junit.jupiter.api.Test;
import pieces.Position.Degree;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DirectionTest {
    @Test
    public void valueOf() {
        assertThat(Direction.valueOf(Degree.of(1, 1))).isEqualTo(Direction.NORTHEAST);
        assertThat(Direction.valueOf(Degree.of(0, 1))).isEqualTo(Direction.NORTH);
        assertThat(Direction.valueOf(Degree.of(1, -2))).isEqualTo(Direction.SSE);
    }
    
    @Test
    public void valueOf_invalid() throws Exception {
        assertThatThrownBy(() -> {
            Direction.valueOf(Degree.of(2, 3));
        }).isInstanceOf(InvalidMovePositionException.class);
    }

    @Test
    public void valueOfDiagonal() throws Exception {
        assertThat(Direction.valueOfDiagonal(Degree.of(1, 1))).isEqualTo(Direction.NORTHEAST);
        assertThat(Direction.valueOfDiagonal(Degree.of(4, 4))).isEqualTo(Direction.NORTHEAST);

        assertThat(Direction.valueOfDiagonal(Degree.of(-1, -1))).isEqualTo(Direction.SOUTHWEST);
        assertThat(Direction.valueOfDiagonal(Degree.of(-4, -4))).isEqualTo(Direction.SOUTHWEST);

        assertThat(Direction.valueOfDiagonal(Degree.of(1, -1))).isEqualTo(Direction.SOUTHEAST);
        assertThat(Direction.valueOfDiagonal(Degree.of(4, -4))).isEqualTo(Direction.SOUTHEAST);

        assertThat(Direction.valueOfDiagonal(Degree.of(-1, 1))).isEqualTo(Direction.NORTHWEST);
        assertThat(Direction.valueOfDiagonal(Degree.of(-4, 4))).isEqualTo(Direction.NORTHWEST);
    }
    
    @Test
    public void valueOfLinear() throws Exception {
        assertThat(Direction.valueOfLinear(Degree.of(0, 1))).isEqualTo(Direction.NORTH);
        assertThat(Direction.valueOfLinear(Degree.of(0, 5))).isEqualTo(Direction.NORTH);

        assertThat(Direction.valueOfLinear(Degree.of(0, -1))).isEqualTo(Direction.SOUTH);
        assertThat(Direction.valueOfLinear(Degree.of(0, -4))).isEqualTo(Direction.SOUTH);

        assertThat(Direction.valueOfLinear(Degree.of(1, 0))).isEqualTo(Direction.EAST);
        assertThat(Direction.valueOfLinear(Degree.of(5, 0))).isEqualTo(Direction.EAST);

        assertThat(Direction.valueOfLinear(Degree.of(-1, 0))).isEqualTo(Direction.WEST);
        assertThat(Direction.valueOfLinear(Degree.of(-5, 0))).isEqualTo(Direction.WEST);
    }
    
    @Test
    public void valueOfEvery() throws Exception {
        assertThat(Direction.valueOfEvery(Degree.of(4, 4))).isEqualTo(Direction.NORTHEAST);
        assertThat(Direction.valueOfEvery(Degree.of(-4, -4))).isEqualTo(Direction.SOUTHWEST);

        assertThat(Direction.valueOfEvery(Degree.of(0, -4))).isEqualTo(Direction.SOUTH);
        assertThat(Direction.valueOfEvery(Degree.of(5, 0))).isEqualTo(Direction.EAST);
    }
}
