/*
 * @class       Winner class
 * @version     1.1.0
 * @date        19.05.13
 * @author      OHSANG SEO (tjdhtkd@gmail.com)
 * @brief       include win cars info. from racing
 */

package racing.model;

import java.util.ArrayList;
import java.util.List;

public class Winner {
    private List<Car> cars;

    public Winner(List<Car> cars) {
        this.cars = cars;
    }

    public List<String> getWinnerNames() {
        List<String> winnerNames = new ArrayList<>();
        int maxDistance = getMaxDistance();

        for (Car car : cars) {
            determineWinner(winnerNames, car, maxDistance);
        }
        return winnerNames;
    }

    private void determineWinner(List<String> winnerNames, Car car, int maxDistance) {
        if (car.isMaxPosition(maxDistance)) {
            winnerNames.add(car.getName());
        }
    }

    private int getMaxDistance() {
        int max = 0;
        for (Car car : cars) {
            max = Math.max(max, car.getPosition());
        }
        return max;
    }
}
