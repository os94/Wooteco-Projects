package ladderGame.view;

import ladderGame.constant.Contants;
import ladderGame.domain.*;

public class OutputView {
    public static void printLadder(Ladder ladder, GameData gameData) {
        printMembers(gameData);
        System.out.println();
        printLadderShape(ladder, gameData);
        printGoals(gameData);
        System.out.println();
    }

    public static void printResultMessage() {
        System.out.println(Contants.MESSAGE_RESULT);
    }

    public static void printDestination(String result) {
        System.out.println(result);
    }

    public static void printDestination(String name, String result) {
        System.out.println(name + " : " + result);
    }

    private static void printMembers(GameData gameData) {
        for (Member member : gameData.getMembers()) {
            System.out.printf("%6s", member.getName());
        }
    }

    private static void printGoals(GameData gameData) {
        for (Goal goal : gameData.getGoals()) {
            System.out.printf("%6s", goal.getGoal());
        }
    }

    private static void printLadderShape(Ladder ladder, GameData gameData) {
        for (Line line : ladder.getLines()) {
            printLine(line, gameData.getCountOfPerson());
        }
    }

    private static void printLine(Line line, int countOfPerson) {
        System.out.print(Contants.MESSAGE_INDENT + Contants.MESSAGE_COLUMN);
        for (int index = 0; index < countOfPerson - 1; index++) {
            printBridge(line, index);
            System.out.print(Contants.MESSAGE_COLUMN);
        }
        System.out.println();
    }

    private static void printBridge(Line line, int index) {
        if (line.isConnected(index)) {
            System.out.print(Contants.MESSAGE_BRIDGE);
        }
        if (!line.isConnected(index)) {
            System.out.print(Contants.MESSAGE_BLANK);
        }
    }
}