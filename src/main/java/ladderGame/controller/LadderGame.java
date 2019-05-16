package ladderGame.controller;

import ladderGame.constant.MessageContants;
import ladderGame.domain.*;
import ladderGame.view.InputView;
import ladderGame.view.OutputView;

import java.util.*;

public class LadderGame {
    private final int STARTROW = 0;

    public void run() {
        String[] names = InputView.inputName().split(",");
        String[] results = InputView.inputResult().split(",");

        GameData gameData = new GameData(makeMembers(names), makeGoal(results));
        int height = InputView.inputHeight();

        Ladder ladder = new Ladder(gameData.getCountOfPerson(), height);

        ladder.connectLine();
        OutputView.print(gameData, ladder);

        int destinationIndex = getDestination(ladder, 0);

        String target = InputView.inputTargetName();

        if (!gameData.hasMember(target) || target.equals("all")) {
            throw new IllegalArgumentException("참가자의 이름이 없습니다.");
        }
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
