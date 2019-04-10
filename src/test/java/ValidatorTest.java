import domain.Validator;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ValidatorTest {
    @Test
    public void getShould_false_when_mismatch_format() {
        assertEquals(Validator.isWinningLotto("1,2,3,4,5,6,7"), false);
        assertEquals(Validator.isWinningLotto("1,2,3,4,5,6,"), false);
        assertEquals(Validator.isWinningLotto("1,2,3,4,5,,6"), false);
        assertEquals(Validator.isWinningLotto(" 1 , 2 , 3 , 4 , 5 ,  6 "), false);
        assertEquals(Validator.isWinningLotto("0,2,3,4,5,46"), false);

    }

    @Test
    public void getShould_false_when_duplicate() {
        assertEquals(Validator.isWinningLotto("1,1,2,3,4,5"), false);
    }
}
