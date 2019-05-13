/*
 * @class       Game class
 * @version     1.1.0
 * @date        19.05.13
 * @author      OHSANG SEO (tjdhtkd@gmail.com)
 * @brief       racing game console
 */

package racing.controller;

import racing.view.InputView;
import racing.view.OutputView;

public class Game {
    private final static String MSG_GAME_RESULT = "실행 결과";

    public static void main(String[] args) {
        String[] carNames = InputView.carName().split(",");
        int count = InputView.gameCount();

        try {
            Racing racing = new Racing(carNames, count);

            OutputView.print(MSG_GAME_RESULT);
            for (int i = 0; i < racing.getCount(); i++) {
                racing.moveCars();
                OutputView.print(racing);
                OutputView.print("");
            }

            OutputView.print(racing.getWinner());
        } catch (IllegalArgumentException e) {
            System.exit(-1);
        }
    }
}
