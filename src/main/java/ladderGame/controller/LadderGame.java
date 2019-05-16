package ladderGame.controller;

import ladderGame.constant.MessageContants;
import ladderGame.domain.*;
import ladderGame.view.InputView;
import ladderGame.view.OutputView;

public class LadderGame {
    private final int STARTROW = 0;

    public void run() {
        GameData gameData = new GameData(InputView.inputName(), InputView.inputResult());
        Ladder ladder = new Ladder(gameData.getCountOfPerson(), InputView.inputHeight());

        ladder.connectLine();

        OutputView.printLadder(gameData, ladder);
        String target = InputView.inputTargetName();
        validateName(gameData, target);
        printGameResult(target, gameData, ladder);
    }

    private void validateName(GameData gameData, String target) {
        if (!gameData.hasMember(target) && !target.equals(MessageContants.MESSAGE_ALL)) {
            throw new IllegalArgumentException(MessageContants.ERROR_NOT_EXIST_MEMBER);
        }
    }

    private void printGameResult(String target, GameData gameData, Ladder ladder) {
        if (target.equals(MessageContants.MESSAGE_ALL)) {
            printResultAll(gameData, ladder);
            return;
        }
        printResult(gameData, ladder, target);
    }

    private void printResultAll(GameData gameData, Ladder ladder) {
        OutputView.printResultMessage();

        for (int i = 0; i < gameData.getCountOfPerson(); i++) {
            String name = gameData.getName(i);
            int destinationIndex = getDestination(ladder, i);
            OutputView.printResult(name, gameData.getResult(destinationIndex));
        }
    }

    private void printResult(GameData gameData, Ladder ladder, String target) {
        OutputView.printResultMessage();
        int destinationIndex = getDestination(ladder, gameData.getIndex(target));
        OutputView.printResult(gameData.getResult(destinationIndex));
    }

    private int getDestination(Ladder ladder, int startPerson) {
        Node currentNode = new Node(STARTROW, startPerson);

        do {
            currentNode.move(getDirection(ladder, currentNode));
        } while (!ladder.atDestination(currentNode));

        return currentNode.getColumn();
    }

    private Direction getDirection(Ladder ladder, Node currentNode) {
        if (Direction.LEFT.canMove(ladder, currentNode)) {
            return Direction.LEFT;
        }
        if (Direction.RIGHT.canMove(ladder, currentNode)) {
            return Direction.RIGHT;
        }
        if (Direction.DOWN.canMove(ladder, currentNode)) {
            return Direction.DOWN;
        }
        throw new IllegalArgumentException();
    }
}
