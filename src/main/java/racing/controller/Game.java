/*
 * @class       Game class
 * @version     1.0.0
 * @date        19.05.10
 * @author      OHSANG SEO (tjdhtkd@gmail.com)
 * @brief       racing game console
 */

package racing.controller;

import racing.model.Winner;
import racing.view.InputView;
import racing.view.OutputView;

public class Game {
    public static void main(String[] args) {
        String[] carNames = InputView.carName().split(",");
        int count = InputView.gameCount();

        try {
            Racing racing = new Racing(carNames, count);
            Winner winner = racing.run();
            OutputView.print(winner);
        } catch (IllegalArgumentException e) {
            System.exit(-1);
        }
    }
}
