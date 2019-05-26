package ladderGame.controller;

import ladderGame.domain.*;
import ladderGame.view.InputView;
import ladderGame.view.OutputView;

public class LadderGame {
    public void run() {
        Members members = new Members(InputView.inputMembers());
        Goals goals = new Goals(InputView.inputGoals(), members.size());
        Ladder ladder = new Ladder(InputView.inputHeight(), members.size());

        ladder.connect();
        OutputView.printLadder(ladder, members, goals);

        LadderResult ladderResult = ladder.play();
        GameResult gameResult = ladderResult.map(members, goals);
        OutputView.printResult(InputView.inputTarget(), gameResult);
    }
}
