package pieces;

import org.junit.jupiter.api.Test;
import pieces.Position.Degree;

import static org.junit.jupiter.api.Assertions.*;

public class DegreeTest {
    @Test
    public void convertToOne() throws Exception {
        assertEquals(1, Degree.convertToOne(3));
        assertEquals(-1, Degree.convertToOne(-3));
        assertEquals(0, Degree.convertToOne(0));
    }
    
    @Test
    public void X_Y_convertToOne() throws Exception {
        Degree degree = Degree.of(4, -4).getDegreeOne();
        assertEquals(1, degree.getXDegree());
        assertEquals(-1, degree.getYDegree());
    }
    
    @Test
    public void isDiagonal() throws Exception {
        Degree degree = Degree.of(4, 4);
        assertTrue(degree.isDiagonal());
        
        degree = Degree.of(4, -4);
        assertTrue(degree.isDiagonal());
        
        degree = Degree.of(-4, 4);
        assertTrue(degree.isDiagonal());
        
        degree = Degree.of(-4, -4);
        assertTrue(degree.isDiagonal());
    }
    
    @Test
    public void isNotDiagonal() throws Exception {
        Degree degree = Degree.of(3, 4);
        assertFalse(degree.isDiagonal());
        
        degree = Degree.of(3, 0);
        assertFalse(degree.isDiagonal());
        
        degree = Degree.of(-3, -4);
        assertFalse(degree.isDiagonal());
    }
    
    @Test
    public void isLinear() throws Exception {
        Degree degree = Degree.of(0, -4);
        assertTrue(degree.isLinear());
        
        degree = Degree.of(-3, 0);
        assertTrue(degree.isLinear());
        
        degree = Degree.of(-3, 2);
        assertFalse(degree.isLinear());
    }
}
