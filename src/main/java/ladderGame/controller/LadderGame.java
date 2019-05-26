package ladderGame.controller;

import ladderGame.constant.Contants;
import ladderGame.domain.*;
import ladderGame.view.InputView;
import ladderGame.view.OutputView;

public class LadderGame {
    private final int START_ROW = 0;

    public void run() {
        Members members = new Members(InputView.inputMembers());
        Goals goals = new Goals(InputView.inputGoals(), members.size());
        Ladder ladder = new Ladder(InputView.inputHeight(), members.size());

        ladder.connectBridges();
        OutputView.printLadder(ladder, members, goals);

        String target = InputView.inputTarget();
        validateName(members, target);
        printGameResult(ladder, members, goals, target);
    }

    private void validateName(Members members, String target) {
        if (!members.has(target) && !target.equals(Contants.MESSAGE_ALL)) {
            throw new IllegalArgumentException(Contants.ERROR_NOT_EXIST_MEMBER);
        }
    }

    private void printGameResult(Ladder ladder, Members members, Goals goals, String target) {
        OutputView.printResultMessage();
        if (target.equals(Contants.MESSAGE_ALL)) {
            printResultAll(ladder, members, goals);
            return;
        }
        printResult(ladder, goals, members.getIndexOf(target));
    }

    private void printResultAll(Ladder ladder, Members members, Goals goals) {
        for (int index = 0; index < members.size(); index++) {
            String name = members.getMember(index);
            int destinationIndex = getDestination(ladder, index);
            OutputView.printDestination(name, goals.getGoal(destinationIndex));
        }
    }

    private void printResult(Ladder ladder, Goals goals, int index) {
        int destinationIndex = getDestination(ladder, index);
        OutputView.printDestination(goals.getGoal(destinationIndex));
    }

    private int getDestination(Ladder ladder, int startPerson) {
        Node currentNode = new Node(START_ROW, startPerson);

        do {
            LadderDirection direction = LadderDirection.valueOf(ladder, currentNode);
            currentNode = direction.move(currentNode);
        } while (!ladder.atDestination(currentNode));

        return currentNode.getColumn();
    }
}
