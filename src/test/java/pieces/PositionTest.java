package pieces;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class PositionTest {
    @Test
    public void create() {
        Position position = new Position("a1");
        assertThat(position.getX()).isEqualTo(0);
        assertThat(position.getY()).isEqualTo(0);
        assertThat(position.getCharPosition()).isEqualTo("a1");
    }

    @Test
    public void create_xy() throws Exception {
        Position position = new Position(0, 0);
        assertThat(position.getCharPosition()).isEqualTo("a1");
    }

    @ParameterizedTest
    @ValueSource(strings = {"a", "a0", "a9", "Z2", "i2"})
    public void create_invalid(String value) throws Exception {
        assertThatExceptionOfType(InvalidPositionException.class)
                .isThrownBy(() -> {
                    new Position(value);
                });
    }

    @Test
    public void getColumnNeighbors() throws Exception {
        Position position = new Position("a2");
        List<Position> neighboars = position.getColumnNeighbors();
        assertThat(neighboars).containsExactly(new Position("a1"), new Position("a3"));

        position = new Position("a1");
        neighboars = position.getColumnNeighbors();
        assertThat(neighboars).containsExactly(new Position("a2"));
    }

    @Test
    public void getMovablePositions() throws Exception {
        Position position = new Position("a2");
        List<Position> positions = position.getMovablePositions(Direction.EAST, new Position("d2"));
        assertThat(positions).hasSize(2);
    }
}
