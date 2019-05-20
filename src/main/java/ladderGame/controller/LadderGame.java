package ladderGame.controller;

import ladderGame.constant.Contants;
import ladderGame.domain.*;
import ladderGame.view.InputView;
import ladderGame.view.OutputView;

public class LadderGame {
    private final int START_ROW = 0;

    public void run() {
        GameData gameData = new GameData(InputView.inputNames(), InputView.inputGoals());
        Ladder ladder = new Ladder(gameData.getCountOfPerson(), InputView.inputHeight());

        ladder.connectBridges();
        OutputView.printLadder(ladder, gameData);

        String target = InputView.inputTargetName();
        validateName(gameData, target);
        printGameResult(ladder, gameData, target);
    }

    private void validateName(GameData gameData, String target) {
        if (!gameData.hasMember(target) && !target.equals(Contants.MESSAGE_ALL)) {
            throw new IllegalArgumentException(Contants.ERROR_NOT_EXIST_MEMBER);
        }
    }

    private void printGameResult(Ladder ladder, GameData gameData, String target) {
        OutputView.printResultMessage();
        if (target.equals(Contants.MESSAGE_ALL)) {
            printResultAll(ladder, gameData);
            return;
        }
        printResult(ladder, gameData, target);
    }

    private void printResultAll(Ladder ladder, GameData gameData) {
        for (int index = 0; index < gameData.getCountOfPerson(); index++) {
            String name = gameData.getName(index);
            int destinationIndex = getDestination(ladder, index);
            OutputView.printDestination(name, gameData.getGoal(destinationIndex));
        }
    }

    private void printResult(Ladder ladder, GameData gameData, String target) {
        int destinationIndex = getDestination(ladder, gameData.getMemberIndex(target));
        OutputView.printDestination(gameData.getGoal(destinationIndex));
    }

    private int getDestination(Ladder ladder, int startPerson) {
        Node currentNode = new Node(START_ROW, startPerson);

        do {
            Direction direction = Direction.valueOf(ladder, currentNode);
            currentNode = direction.move(currentNode);
        } while (!ladder.atDestination(currentNode));

        return currentNode.getColumn();
    }
}
