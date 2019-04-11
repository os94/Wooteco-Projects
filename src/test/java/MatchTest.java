/*
 * @class       MatchTest class
 * @version     1.0.0
 * @date        19.04.11
 * @author      OHSANG SEO (tjdhtkd@gmail.com)
 * @brief       test code for match Lotto.
 */

import domain.*;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class MatchTest {
    @Test
    public void matchTest() {
        WinningLotto winningLotto = new WinningLotto(new Lotto(Arrays.asList(1,2,3,4,5,6)), 45);

        Rank expectFirst = winningLotto.match(new Lotto(Arrays.asList(1,2,3,4,5,6)));
        assertEquals(expectFirst, Rank.FIRST);

        Rank expectSecond = winningLotto.match(new Lotto(Arrays.asList(1,2,3,4,5,45)));
        assertEquals(expectSecond, Rank.SECOND);

        Rank expectThird = winningLotto.match(new Lotto(Arrays.asList(1,2,3,4,5,10)));
        assertEquals(expectThird, Rank.THIRD);

        Rank expectFourth = winningLotto.match(new Lotto(Arrays.asList(1,2,3,4,11,10)));
        assertEquals(expectFourth, Rank.FOURTH);

        Rank expectFifth = winningLotto.match(new Lotto(Arrays.asList(1,2,3,12,11,10)));
        assertEquals(expectFifth, Rank.FIFTH);

        Rank expectMiss = winningLotto.match(new Lotto(Arrays.asList(1,2,13,12,11,10)));
        assertEquals(expectMiss, Rank.MISS);
    }
}
