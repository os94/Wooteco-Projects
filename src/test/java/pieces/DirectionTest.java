package pieces;

import org.junit.jupiter.api.Test;
import pieces.Position.Degree;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DirectionTest {
    @Test
    public void valueOf() {
        assertEquals(Direction.NORTHEAST, Direction.valueOf(Degree.of(1, 1)));
        assertEquals(Direction.NORTH, Direction.valueOf(Degree.of(0, 1)));
        assertEquals(Direction.SSE, Direction.valueOf(Degree.of(1, -2)));
    }
    
    @Test
    public void valueOf_invalid() throws Exception {
        assertThatThrownBy(() -> {
            Direction.valueOf(Degree.of(2, 3));
        }).isInstanceOf(InvalidMovePositionException.class);
    }
    
    @Test
    public void valueOfDiagonal() throws Exception {
        assertEquals(Direction.NORTHEAST, Direction.valueOfDiagonal(Degree.of(1, 1)));
        assertEquals(Direction.NORTHEAST, Direction.valueOfDiagonal(Degree.of(4, 4)));

        assertEquals(Direction.SOUTHWEST, Direction.valueOfDiagonal(Degree.of(-1, -1)));
        assertEquals(Direction.SOUTHWEST, Direction.valueOfDiagonal(Degree.of(-4, -4)));
        
        assertEquals(Direction.SOUTHEAST, Direction.valueOfDiagonal(Degree.of(1, -1)));
        assertEquals(Direction.SOUTHEAST, Direction.valueOfDiagonal(Degree.of(4, -4)));
        
        assertEquals(Direction.NORTHWEST, Direction.valueOfDiagonal(Degree.of(-1, 1)));
        assertEquals(Direction.NORTHWEST, Direction.valueOfDiagonal(Degree.of(-4, 4)));
    }
    
    @Test
    public void valueOfLinear() throws Exception {
        assertEquals(Direction.NORTH, Direction.valueOfLinear(Degree.of(0, 1)));
        assertEquals(Direction.NORTH, Direction.valueOfLinear(Degree.of(0, 5)));
        
        assertEquals(Direction.SOUTH, Direction.valueOfLinear(Degree.of(0, -1)));
        assertEquals(Direction.SOUTH, Direction.valueOfLinear(Degree.of(0, -4)));
        
        assertEquals(Direction.EAST, Direction.valueOfLinear(Degree.of(1, 0)));
        assertEquals(Direction.EAST, Direction.valueOfLinear(Degree.of(5, 0)));
        
        assertEquals(Direction.WEST, Direction.valueOfLinear(Degree.of(-1, 0)));
        assertEquals(Direction.WEST, Direction.valueOfLinear(Degree.of(-5, 0)));
    }
    
    @Test
    public void valueOfEvery() throws Exception {
        assertEquals(Direction.NORTHEAST, Direction.valueOfEvery(Degree.of(4, 4)));
        assertEquals(Direction.SOUTHWEST, Direction.valueOfEvery(Degree.of(-4, -4)));
        
        assertEquals(Direction.SOUTH, Direction.valueOfEvery(Degree.of(0, -4)));
        assertEquals(Direction.EAST, Direction.valueOfEvery(Degree.of(5, 0)));
    }
}
