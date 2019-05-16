package ladderGame.controller;

import ladderGame.constant.MessageContants;
import ladderGame.domain.*;
import ladderGame.view.InputView;
import ladderGame.view.OutputView;

import java.util.*;

public class LadderGame {
    public void run() {
        String[] names = InputView.inputName().split(",");
        String[] results = InputView.inputResult().split(",");

        GameData gameData = new GameData(makeMembers(names), makeGoal(results));
        int height = InputView.inputHeight();

        Ladder ladder = new Ladder(gameData.getCountOfPerson(), height);

        ladder.connectLine();
        OutputView.print(gameData, ladder);
    }

    private List<Member> makeMembers(String[] names) {
        List<Member> memberGroup = new ArrayList<>();

        if (hasDuplicateName(names)) {
            throw new IllegalArgumentException(MessageContants.ERROR_DUPLICATE_NAME);
        }
        for (String name : names) {
            memberGroup.add(new Member(name));
        }

        return memberGroup;
    }

    private List<Goal> makeGoal(String[] results) {
        List<Goal> goals = new ArrayList<>();

        for (String result : results) {
            goals.add(new Goal(result));
        }

        return goals;
    }

    private boolean hasDuplicateName(String[] names) {
        return new HashSet<>(Arrays.asList(names)).size() != names.length;
    }
}
