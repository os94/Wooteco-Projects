/*
 * @class       RacingTest class
 * @version     1.0.0
 * @date        19.05.10
 * @author      OHSANG SEO (tjdhtkd@gmail.com)
 * @brief       test duplicate car name, negative move number
 */

package racing.controller;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class RacingTest {
    @Test
    void 차량이름_중복() {
        String[] carNames = {"pobi", "sean", "pobi"};

        assertThrows(IllegalArgumentException.class, () -> {
            new Racing(carNames, 3);
        });
    }

    @Test
    void 이동횟수_음수() {
        String[] carNames = {"pobi", "sean"};

        assertThrows(IllegalArgumentException.class, () -> {
            new Racing(carNames, -1);
        });
    }
}
