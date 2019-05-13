/*
 * @class       WinnerTest class
 * @version     1.0.0
 * @date        19.05.10
 * @author      OHSANG SEO (tjdhtkd@gmail.com)
 * @brief       test getWinners method
 */

package racing.model;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class WinnerTest {
    @Test
    void 우승자_선정() {
        List<Car> cars = Arrays.asList(
                new Car("pobi", 3), new Car("sean", 2)
                , new Car("denis", 3), new Car("ms", 1));
        Winner winner = new Winner(cars);

        assertThat(winner.getWinners()).containsExactlyInAnyOrder("pobi", "denis");
    }
}
