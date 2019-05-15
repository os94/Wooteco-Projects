package ladderGame.controller;

import ladderGame.domain.Ladder;
import ladderGame.view.InputView;

public class LadderGame {
    public static void run() {
        String[] names = InputView.inputName().split(",");
        int height = InputView.inputHeight();
        int countOfPerson = names.length;

        Ladder ladder = new Ladder(countOfPerson, height);
        ladder.connectLine();
    }
}
