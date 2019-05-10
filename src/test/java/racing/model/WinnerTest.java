/*
 * @class       WinnerTest class
 * @version     1.0.0
 * @date        19.05.10
 * @author      OHSANG SEO (tjdhtkd@gmail.com)
 * @brief       test getWinners method
 */

package racing.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class WinnerTest {
    @Test
    void 우승자_선정() {
        List<Car> cars = new ArrayList<>();
        cars.add(new Car("pobi", 3));
        cars.add(new Car("sean", 2));
        cars.add(new Car("denis", 3));
        cars.add(new Car("ms", 1));
        Winner winner = new Winner(cars);

        List<String> winCars = new ArrayList<>();
        winCars.add("pobi");
        winCars.add("denis");

        assertThat(winner.getWinners()).isEqualTo(winCars);
    }
}
